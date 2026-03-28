package modeep.hear.infrastructure.adapter.`in`.chat.dto.response

import modeep.hear.domain.chat.vo.SuggestionType
import java.util.UUID

@Deprecated("use CreateMessageResponse instead")
data class CreateVoiceMessageResponse(
    val chatId: UUID,
    val userTranscription: String,
    val aiResponseText: String,
    val aiAudioUrl: String,
    val suggestion: SuggestionType? = null
)
