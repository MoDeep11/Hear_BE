package modeep.hear.domain.chat.port.`in`

import modeep.hear.domain.user.model.User
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import java.util.UUID

interface CreateMessageUseCase {
    suspend fun executeText(
        chatId: UUID,
        request: CreateMessageRequest,
        user: User
    ): CreateMessageResponse

    suspend fun executeVoice(
        chatId: UUID,
        request: CreateVoiceMessageRequest,
        user: User
    ): CreateMessageResponse
}
