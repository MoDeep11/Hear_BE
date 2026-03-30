package modeep.hear.infrastructure.adapter.`in`.user.dto.response

import java.time.LocalDateTime

data class UpdateProfileImageResponse(
    val profileImageUrl: String,
    val updatedAt: LocalDateTime
)