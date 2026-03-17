package modeep.hear.domain.auth.model

data class BlacklistToken(
    val accessToken: String,
    val userId: String,
    val timeToLive: Long
)
