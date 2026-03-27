package modeep.hear.domain.chat.port.out.external

import modeep.hear.domain.chat.model.Message
import modeep.hear.infrastructure.adapter.out.chat.external.dto.request.SendMessageRequest

interface FetchChatPort {
    suspend fun sendMessage(request: SendMessageRequest): Message
}