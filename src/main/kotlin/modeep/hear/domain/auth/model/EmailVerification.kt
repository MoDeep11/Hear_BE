package modeep.hear.domain.auth.model

data class EmailVerification(
    val email: String,
    val code: String,
    val timeToLive: Long = 180L
)
