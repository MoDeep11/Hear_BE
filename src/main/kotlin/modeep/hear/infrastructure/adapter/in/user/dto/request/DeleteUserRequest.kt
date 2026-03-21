package modeep.hear.infrastructure.adapter.`in`.user.dto.request

data class DeleteUserRequest(
    val password: String,
    val refreshToken: String
)

// RawPassword VO를 만들어 인코딩된 비밀번호 / 평문 비밀번호 구분하는 방법도 있음
