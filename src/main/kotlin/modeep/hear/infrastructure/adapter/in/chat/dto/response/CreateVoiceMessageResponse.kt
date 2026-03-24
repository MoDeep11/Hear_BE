package modeep.hear.infrastructure.adapter.`in`.chat.dto.response

import modeep.hear.domain.chat.vo.SuggestionType
import java.util.UUID

data class CreateVoiceMessageResponse(
    val sessionId: UUID,
    val userTranscription: String,
    val aiResponseText: String,
    val aiAudioUrl: String,
    val suggestion: SuggestionType? = null
)
