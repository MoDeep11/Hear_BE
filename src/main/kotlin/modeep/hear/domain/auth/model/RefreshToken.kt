package modeep.hear.domain.auth.model

import java.util.UUID

data class RefreshToken(
    val refreshToken: String,
    val userId: UUID,
    val timeToLive: Long
)
