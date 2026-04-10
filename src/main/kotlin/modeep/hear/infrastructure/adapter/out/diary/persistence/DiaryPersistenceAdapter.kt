package modeep.hear.infrastructure.adapter.out.diary.persistence

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.out.command.CommandDiaryPort
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.infrastructure.adapter.out.diary.persistence.mapper.DiaryMapper
import modeep.hear.infrastructure.adapter.out.diary.persistence.repository.DiaryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.util.UUID

@Component
class DiaryPersistenceAdapter(
    private val repo: DiaryRepository,
    private val mapper: DiaryMapper
) : QueryDiaryPort, CommandDiaryPort {
    // --Query--//
    override fun findById(diaryId: UUID): Diary? {
        return repo.findByIdWithImages(diaryId) ?.let { mapper.toModel(it) }
    }

    override fun findIdsByFilters(
        userId: UUID,
        yearMonth: YearMonth,
        hasPhoto: Boolean,
        imageType: DiarySourceType,
        tag: String?,
        pageable: Pageable
    ): List<UUID> {
        val start = yearMonth.atDay(1).atStartOfDay()
        val end = yearMonth.plusMonths(1).atDay(1).atStartOfDay()

        return repo.findIdsByFilters(userId, start, end, imageType.name, hasPhoto, tag, pageable)
    }

    override fun findAllByIdInWithImages(ids: List<UUID>): List<Diary> {
        return repo.findAllByIdInWithImages(ids)
            .map { it.let(mapper::toModel) }
    }

    override fun findDistinctDatesByUserId(userId: UUID, limit: Int): List<LocalDate> {
        val pageable = PageRequest.of(0, limit)
        return repo.findDistinctDatesByUserId(userId, pageable)
    }

    override fun existsByUserIdAndDate(userId: UUID, date: LocalDate): Boolean {
        val start = date.atStartOfDay()
        val end = date.atTime(LocalTime.MAX)

        return repo.existsByUserIdAndDateRange(userId, start, end)
    }

    override fun findLatestByUserIdAndDate(userId: UUID, date: LocalDate): Diary? {
        val start = date.atStartOfDay()
        val end = date.atTime(LocalTime.MAX)

        return repo.findLatestDiary(userId, start, end)
            ?.let { mapper.toModel(it) }
    }

    override fun countByUserId(userId: UUID): Long {
        return repo.countByUserId(userId)
    }

    override fun findAllByUserIdAndYearMonth(userId: UUID, yearMonth: YearMonth): List<Diary> {
        val start = yearMonth.atDay(1).atStartOfDay()
        val end = yearMonth.plusMonths(1).atDay(1).atStartOfDay()
        return repo.findAllByUserIdAndBaseTimeCreatedAtGreaterThanEqualAndBaseTimeCreatedAtLessThan(userId, start, end).map { mapper.toModel(it) }
    }

    override fun countByUserIdAndYearMonthWithAiImage(userId: UUID, yearMonth: YearMonth): Int {
        val start = yearMonth.atDay(1).atStartOfDay()
        val end = yearMonth.plusMonths(1).atDay(1).atStartOfDay()
        return repo.countByUserIdAndCreatedAtBetweenWithAiImage(userId, start, end)
    }

    override fun findAllByCreatedAtBetween(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<Diary> {
        return repo.findAllByDateRange(start, end).map { mapper.toModel(it) }
    }

    override fun findRandomByUserId(userId: UUID): Diary? {
        val count = repo.countByUserId(userId)
        if (count == 0L) return null
        val offset = (Math.random() * count).toLong()
        return repo.findOneByUserIdWithOffset(userId, offset)?.let { mapper.toModel(it) }
    }

    // --Command--//
    override fun save(diary: Diary) {
        val isExist = repo.existsById(diary.id)
        val entity = mapper.toEntity(diary, isNew = !isExist)
        repo.save(entity)
    }

    override fun deleteById(diaryId: UUID) {
        repo.deleteById(diaryId)
    }
}
