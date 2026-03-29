package modeep.hear.infrastructure.adapter.out.chat.external.dto.response

import java.util.UUID

data class InitChatResponse(
    val chatId: UUID,
    val initialMessage: String
)
