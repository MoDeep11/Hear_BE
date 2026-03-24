package modeep.hear.domain.chat.port.`in`

import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import java.util.UUID

interface CreateMessageUseCase {
    fun execute(
        chatId: UUID,
        request: CreateMessageRequest
    ): CreateMessageResponse
}