package modeep.hear.infrastructure.adapter.out.diary.persistence

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.infrastructure.adapter.out.diary.persistence.mapper.DiaryMapper
import modeep.hear.infrastructure.adapter.out.diary.persistence.repository.DiaryRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.time.YearMonth
import java.util.UUID

@Component
class DiaryPersistenceAdapter(
    private val repo: DiaryRepository,
    private val mapper: DiaryMapper
) : DiaryPort {
    // --Query--//
    override fun findById(diaryId: UUID): Diary? {
        return repo.findByIdWithImages(diaryId) ?.let { mapper.toModel(it) }
    }

    override fun findIdsByFilters(
        yearMonth: YearMonth,
        hasPhoto: Boolean,
        imageType: DiarySourceType,
        tag: String?,
        pageable: Pageable
    ): List<UUID> {
        val start = yearMonth.atDay(1).atStartOfDay()
        val end = yearMonth.plusMonths(1).atDay(1).atStartOfDay()

        return repo.findIdsByFilters(start, end, imageType.name, hasPhoto, tag, pageable)
    }

    override fun findAllByIdInWithImages(ids: List<UUID>): List<Diary> {
        return repo.findAllByIdInWithImages(ids)
            .map { it.let(mapper::toModel) }
    }

    // --Command--//
    override fun save(diary: Diary) {
        repo.save(mapper.toEntity(diary))
    }

    override fun deleteById(diaryId: UUID) {
        repo.deleteIfExists(diaryId)
    }
}
