package modeep.hear.domain.user.model

import java.time.LocalDateTime
import java.util.UUID

data class UserProfile(
    val userId: UUID? = null,
    val nickname: String,
    val profileImageUrl: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)