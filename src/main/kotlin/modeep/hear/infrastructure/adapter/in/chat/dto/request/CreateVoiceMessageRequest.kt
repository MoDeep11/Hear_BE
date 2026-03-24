package modeep.hear.infrastructure.adapter.`in`.chat.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateVoiceMessageRequest(
    @field:NotBlank
    val voiceUrl: String
)
