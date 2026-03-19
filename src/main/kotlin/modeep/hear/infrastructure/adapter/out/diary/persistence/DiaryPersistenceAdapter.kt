package modeep.hear.infrastructure.adapter.out.diary.persistence

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.infrastructure.adapter.out.diary.mapper.DiaryMapper
import modeep.hear.infrastructure.adapter.out.diary.persistence.repository.DiaryRepository
import org.springframework.data.domain.Pageable
import java.time.YearMonth
import java.util.UUID

class DiaryPersistenceAdapter(
    private val repo: DiaryRepository,
    private val mapper: DiaryMapper
) : DiaryPort {
    // --Query--//
    override fun findById(diaryId: UUID): Diary? {
        return repo.findByIdWithImages(diaryId) ?.let { mapper.toModel(it) }
    }

    override fun findAllByMonthWithFilters(
        yearMonth: YearMonth,
        hasPhoto: Boolean,
        imageType: DiarySourceType,
        tag: String?,
        pageable: Pageable,
    ): List<Diary> {
        val start = yearMonth.atDay(1).atStartOfDay()
        val end = yearMonth.plusMonths(1).atDay(1).atStartOfDay()

        return repo.findAllByMonthWithFilters(start, end, imageType, hasPhoto, tag, pageable)
            .map { it.let(mapper::toModel) }
    }
}
