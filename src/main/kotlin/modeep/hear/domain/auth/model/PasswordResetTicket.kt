package modeep.hear.domain.auth.model

data class PasswordResetTicket(
    val ticket: String,
    val email: String,
    val timeToLive: Long = 300L
)
