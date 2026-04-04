package modeep.hear.domain.user.event

import modeep.hear.domain.common.vo.Emotion
import java.time.LocalDate
import java.util.UUID

data class IncreasedUserStatEvent(
    val userId: UUID,
    val diaryId: UUID,
    val emotion: Emotion,
    val diaryDate: LocalDate
)
