package modeep.hear.domain.diary.event

import java.util.UUID

data class DiaryCreatedEvent(
    val userId: UUID
)