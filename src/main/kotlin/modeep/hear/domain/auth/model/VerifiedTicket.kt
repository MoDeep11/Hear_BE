package modeep.hear.domain.auth.model

data class VerifiedTicket(
    val ticket: String,
    val email: String,
    val timeToLive: Long = 600L
)
