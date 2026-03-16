package modeep.hear.domain.auth.model

data class RefreshToken(
    val refreshToken: String,
    val timeToLive: Long
)
