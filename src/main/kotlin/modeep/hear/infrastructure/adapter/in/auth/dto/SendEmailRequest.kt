package modeep.hear.infrastructure.adapter.`in`.auth.dto

data class SendEmailRequest(
    val email: String,
    val type: EmailRequestType,
)
