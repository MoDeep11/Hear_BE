package modeep.hear.domain.chat.port.dto.result

import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.vo.SuggestionType
import modeep.hear.infrastructure.adapter.out.chat.external.dto.response.SendMessageResponseStatus

data class SendMessageResult(
    val userTranscription: String,
    val status: SendMessageResponseStatus,
    val suggestion: SuggestionType? = null,
    val aiMessage: Message
)
