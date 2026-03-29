package modeep.hear.infrastructure.adapter.`in`.chat.dto.response

import modeep.hear.domain.chat.vo.SuggestionType
import java.util.UUID

@Deprecated(
    message = "use CreateMessageResponse instead",
    replaceWith = ReplaceWith("CreateMessageResponse")
)
data class CreateVoiceMessageResponse(
    val chatId: UUID,
    val userTranscription: String,
    val aiResponseText: String,
    val aiAudioUrl: String,
    val suggestion: SuggestionType? = null
)
