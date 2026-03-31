package modeep.hear.infrastructure.adapter.`in`.chat.dto.response

import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.domain.chat.vo.SuggestionType
import java.time.LocalDateTime
import java.util.UUID

data class CreateMessageResponse(
    val chatId: UUID,
    val userContent: String,
    val aiContent: String,
    val aiAudioUrl: String?,
    val status: ChatStatus,
    val suggestion: SuggestionType? = null,
    val userCreatedAt: LocalDateTime,
    val aiCreatedAt: LocalDateTime
)
