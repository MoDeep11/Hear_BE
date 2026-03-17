package modeep.hear.infrastructure.adapter.`in`.auth.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank
    @field:Size(max = 100)
    val email: String,

    @field:NotBlank
    @field:Size(min = 8, max = 100)
    val password: String
)
