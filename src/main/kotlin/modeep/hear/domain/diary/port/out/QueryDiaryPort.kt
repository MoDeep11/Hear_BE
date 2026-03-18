package modeep.hear.domain.diary.port.out

import modeep.hear.domain.diary.model.Diary
import java.time.YearMonth
import java.util.UUID

interface QueryDiaryPort {
    fun findById(diaryId: UUID): Diary?
    fun findAllByYearMonth(yearMonth: YearMonth): List<Diary>
}
