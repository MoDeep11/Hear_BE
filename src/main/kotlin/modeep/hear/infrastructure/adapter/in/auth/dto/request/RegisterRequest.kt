package modeep.hear.infrastructure.adapter.`in`.auth.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank
    @field:Size(max = 100)
    @field:Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    val email: String,

    @field:NotBlank
    @field:Size(min = 8, max = 100)
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W)(?=\\S+$).+$", message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.")
    val password: String,

    @field:JsonAlias("confirm_password")
    @field:NotBlank
    @field:Size(min = 8, max = 100)
    val confirmPassword: String,

    @field:NotBlank
    val ticket: String
)
