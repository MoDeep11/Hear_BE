package modeep.hear.infrastructure.adapter.`in`.chat.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateMessageRequest(
    @field:NotBlank
    @field:Size(max = 2000)
    val message: String
)
