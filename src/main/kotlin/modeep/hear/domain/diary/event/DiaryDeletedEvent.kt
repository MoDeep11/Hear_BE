package modeep.hear.domain.diary.event

import java.time.LocalDateTime
import java.util.UUID

data class DiaryDeletedEvent(
    val userId: UUID,
    val deletedTime: LocalDateTime
)
