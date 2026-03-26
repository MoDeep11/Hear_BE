package modeep.hear.infrastructure.adapter.`in`.chat.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class CreateChatResponse(
    val chatId: UUID,
    val initialMessage: String,
    val createdAt: LocalDateTime
)
