package modeep.hear.infrastructure.adapter.`in`.auth.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class CreateUserRequest(
    @field:NotBlank
    @field:Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")
    val email: String,

    @field:NotBlank
    @field:Size(min = 8, max = 100)
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W)(?=\\S+$).+$")
    val password: String,

    @field:NotBlank
    @field:Size(min = 8, max = 100)
    val confirmPassword: String
)
