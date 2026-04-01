package modeep.hear.infrastructure.adapter.`in`.user.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdatePasswordRequest(
    @JsonAlias("old_password")
    @field:NotBlank
    val oldPassword: String,

    @JsonAlias("new_password")
    @field:NotBlank
    @field:Size(min = 8, max = 100)
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W)(?=\\S+$).+$")
    val newPassword: String,

    @JsonAlias("confirm_password")
    @field:NotBlank
    @field:Size(min = 8, max = 100)
    val confirmPassword: String
)
