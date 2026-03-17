package modeep.hear.infrastructure.adapter.`in`.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class LogoutRequest(
    @field:NotBlank
    val refreshToken: String
)
