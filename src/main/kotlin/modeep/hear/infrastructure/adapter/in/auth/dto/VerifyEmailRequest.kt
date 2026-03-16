package modeep.hear.infrastructure.adapter.`in`.auth.dto

data class VerifyEmailRequest(
    val email: String,
    val type: EmailRequestType,
    val code: String,
)
