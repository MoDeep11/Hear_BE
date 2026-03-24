package modeep.hear.infrastructure.adapter.`in`.chat.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateMessageRequest(
    @field:NotBlank
    val message: String
)