package modeep.hear.infrastructure.adapter.`in`.auth.dto.request

import jakarta.validation.constraints.NotBlank
import modeep.hear.infrastructure.adapter.`in`.auth.dto.EmailRequestType

data class SendEmailRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val type: EmailRequestType
)
