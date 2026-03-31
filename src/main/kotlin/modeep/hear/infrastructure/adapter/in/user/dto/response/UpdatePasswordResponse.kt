package modeep.hear.infrastructure.adapter.`in`.user.dto.response

import java.time.LocalDateTime

data class UpdatePasswordResponse(
    val emailSent: Boolean,
    val sentTo: String?,
    val updatedAt: LocalDateTime
)
