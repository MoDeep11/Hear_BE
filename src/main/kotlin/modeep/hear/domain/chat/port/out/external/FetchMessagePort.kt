package modeep.hear.domain.chat.port.out.external

import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.dto.result.SendMessageResult
import java.util.UUID

interface FetchMessagePort {
    suspend fun sendMessage(chatId: UUID, message: Message): SendMessageResult
}
