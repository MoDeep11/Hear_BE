package modeep.hear.infrastructure.adapter.`in`.user.dto.request

import jakarta.validation.constraints.NotBlank

data class DeleteUserRequest(
    @field:NotBlank
    val password: String,

    @field:NotBlank
    val refreshToken: String
)

// RawPassword VO를 만들어 인코딩된 비밀번호 / 평문 비밀번호 구분하는 방법도 있음
