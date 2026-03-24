package modeep.hear.infrastructure.adapter.`in`.chat.dto.response

import modeep.hear.domain.chat.vo.SuggestionType
import java.time.LocalDateTime
import java.util.UUID

data class CreateMessageResponse(
    val sessionId: UUID,
    val content: String,
    val createdAt: LocalDateTime,
    val suggestion: SuggestionType? = null
)
