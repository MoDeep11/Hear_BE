package modeep.hear.domain.auth.model

data class BlacklistToken(
    val accessToken: String,
    val timeToLive: Long
)
