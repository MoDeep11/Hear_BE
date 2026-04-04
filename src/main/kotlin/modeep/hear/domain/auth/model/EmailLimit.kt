package modeep.hear.domain.auth.model

data class EmailLimit(
    val email: String,
    val count: Int,
    val timeToLive: Long = 3600L
)
