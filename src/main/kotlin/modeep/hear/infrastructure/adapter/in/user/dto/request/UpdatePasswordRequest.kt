package modeep.hear.infrastructure.adapter.`in`.user.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdatePasswordRequest(
    @field:NotBlank
    val oldPassword: String,

    @field:NotBlank
    @field:Size(min = 8, max = 100)
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W)(?=\\S+$).+$")
    val newPassword: String,

    @field:NotBlank
    @field:Size(min = 8, max = 100)
    val confirmPassword: String
)
