package modeep.hear.domain.chat.port.`in`

import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateVoiceMessageResponse
import java.util.UUID

@Deprecated("use CreateMessageUseCase instead")
interface CreateVoiceMessageUseCase {
    suspend fun execute(
        chatId: UUID,
        request: CreateVoiceMessageRequest
    ): CreateVoiceMessageResponse
}
