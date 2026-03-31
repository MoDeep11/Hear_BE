package modeep.hear.infrastructure.adapter.`in`.user.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class UserProfileResponse(
    val userId: UUID,
    val email: String,
    val nickname: String,
    val profileImageUrl: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
