package modeep.hear.domain.diary.port.`in`

import modeep.hear.domain.diary.model.Diary
import java.util.UUID

interface QueryDiaryDetailUseCase {
    fun execute(diaryId: UUID) : Diary
}