package modeep.hear.infrastructure.adapter.out.chat.external.dto.response

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank
import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.domain.chat.vo.SuggestionType
import java.util.UUID

data class SendMessageResponse(
    @field:NotBlank
    @field:JsonAlias("user_transcription")
    val userTranscription: String,

    @field:NotBlank
    @field:JsonAlias("ai_response_text")
    val aiResponseText: String,

    @field:JsonAlias("ai_audio_url")
    val aiAudioUrl: String? = null,

    val status: ChatStatus,

    val suggestion: SuggestionType? = null,

    @field:JsonAlias("chat_id")
    val chatId: UUID
)
