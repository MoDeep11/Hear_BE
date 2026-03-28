package modeep.hear.infrastructure.adapter.out.chat.external.dto.response

import jakarta.validation.constraints.NotBlank
import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.domain.chat.vo.SuggestionType
import java.util.UUID

data class SendMessageResponse(
    @field:NotBlank
    val userTranscription: String,

    @field:NotBlank
    val aiResponseText: String,

    val aiAudioUrl: String? = null,

    val status: ChatStatus = ChatStatus.CONTINUE,

    val suggestion: SuggestionType? = null,

    @field:NotBlank
    val chatId: UUID
)
