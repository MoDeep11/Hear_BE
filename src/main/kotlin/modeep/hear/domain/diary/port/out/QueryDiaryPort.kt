package modeep.hear.domain.diary.port.out

import modeep.hear.domain.diary.model.Diary
import java.util.UUID

interface QueryDiaryPort {
    fun findById(diaryId: UUID) : Diary?
}