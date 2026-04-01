package modeep.hear.infrastructure.adapter.`in`.user.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdatePasswordRequest(
    @field:JsonAlias("old_password")
    @field:NotBlank
    val oldPassword: String,

    @field:JsonAlias("new_password")
    @field:NotBlank
    @field:Size(min = 8, max = 100)
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W)(?=\\S+$).+$", message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.")
    val newPassword: String,

    @field:JsonAlias("confirm_password")
    @field:NotBlank
    @field:Size(min = 8, max = 100)
    val confirmPassword: String
)
