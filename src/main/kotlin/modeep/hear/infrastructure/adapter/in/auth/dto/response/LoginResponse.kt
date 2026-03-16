package modeep.hear.infrastructure.adapter.`in`.auth.dto.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)