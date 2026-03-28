package modeep.hear.domain.diary.event

import java.util.UUID

data class DiaryDeletedEvent(
    val userId: UUID
)