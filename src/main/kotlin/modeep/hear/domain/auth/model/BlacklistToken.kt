package modeep.hear.domain.auth.model

import java.util.UUID

data class BlacklistToken(
    val accessToken: String,
    val userId: UUID,
    val timeToLive: Long
)
