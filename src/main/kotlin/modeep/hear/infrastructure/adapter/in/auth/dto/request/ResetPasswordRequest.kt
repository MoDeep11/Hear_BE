package modeep.hear.infrastructure.adapter.`in`.auth.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class ResetPasswordRequest(
    @field:NotBlank
    val ticket: String,

    @field:NotBlank
    @field:Size(min = 8, max = 100)
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W)(?=\\S+$).+$")
    val password: String,

    @field:NotBlank
    val confirmPassword: String
)
