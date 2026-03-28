package modeep.hear.domain.chat.port.dto.result

import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.domain.chat.vo.SuggestionType

data class SendMessageResult(
    val userTranscription: String,
    val status: ChatStatus,
    val suggestion: SuggestionType? = null,
    val aiMessage: Message
)
