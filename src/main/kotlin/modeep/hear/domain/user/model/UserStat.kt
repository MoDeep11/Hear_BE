package modeep.hear.domain.user.model

import java.time.LocalDateTime
import java.util.UUID

data class UserStat(
    val userId: UUID? = null,
    val currentStreak: Int,
    val totalDiaries: Int,
    val maxStreak: Int,
    val lastWrittenAt: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)