package modeep.hear.infrastructure.adapter.`in`.chat.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank

data class CreateVoiceMessageRequest(
    @field:JsonAlias("voice_url")
    @field:NotBlank
    val voiceUrl: String,
    val duration: Long = 0L // milliseconds
)
