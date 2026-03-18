package modeep.hear.infrastructure.adapter.out.diary.persistence

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.infrastructure.adapter.out.diary.mapper.DiaryMapper
import modeep.hear.infrastructure.adapter.out.diary.persistence.repository.DiaryRepository
import java.time.YearMonth
import java.util.UUID

class DiaryPersistenceAdapter(
    private val repo: DiaryRepository,
    private val mapper: DiaryMapper,
) : DiaryPort {
    //--Query--//
    override fun findById(diaryId: UUID): Diary? {
        return repo.findByIdWithImages(diaryId) ?.let { mapper.toModel(it) }
    }

    override fun findAllByYearMonth(yearMonth: YearMonth): List<Diary?> {
        val start = yearMonth.atDay(1).atStartOfDay()
        val end = yearMonth.plusMonths(1).atDay(1).atStartOfDay()

        return repo.findAllByMonthWithImages(start, end)
            .map { it.let(mapper::toModel) }
    }
}
