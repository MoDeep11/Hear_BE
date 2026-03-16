package modeep.hear.domain.auth.port.`in`.dto

import java.time.LocalDateTime

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessExpiredAt: LocalDateTime,
    val refreshExpiredAt: LocalDateTime
)
