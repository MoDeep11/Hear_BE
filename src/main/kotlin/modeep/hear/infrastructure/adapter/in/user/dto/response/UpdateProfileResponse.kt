package modeep.hear.infrastructure.adapter.`in`.user.dto.response

import java.time.LocalDateTime

data class UpdateProfileResponse(
    val nickname: String,
    val profileImageUrl: String,
    val updatedAt: LocalDateTime
)
