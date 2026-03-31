package modeep.hear.domain.chat.port.`in`

import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import java.util.UUID

interface CreateMessageUseCase {
    suspend fun executeText(
        chatId: UUID,
        request: CreateMessageRequest
    ): CreateMessageResponse

    suspend fun executeVoice(
        chatId: UUID,
        request: CreateVoiceMessageRequest
    ): CreateMessageResponse
}
