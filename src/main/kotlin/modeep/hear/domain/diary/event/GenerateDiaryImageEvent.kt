package modeep.hear.domain.diary.event

import modeep.hear.domain.diary.model.Diary
import java.util.UUID

data class GenerateDiaryImageEvent(
    val userId: UUID,
    val chatId: UUID,
    val diary: Diary
)