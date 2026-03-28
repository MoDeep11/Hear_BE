package modeep.hear.infrastructure.adapter.out.diary.event

import java.util.UUID

data class DiaryDeletedEvent(
    val userId: UUID
)