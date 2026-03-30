package modeep.hear.domain.user.event

import java.time.LocalDateTime
import java.util.UUID

data class DecreasedUserStatEvent(
    val now: LocalDateTime,
    val userId: UUID,
    val createdAtOfDiary: LocalDateTime,
    val hasTodayDiary: Boolean,
    val totalCount: Int
)
