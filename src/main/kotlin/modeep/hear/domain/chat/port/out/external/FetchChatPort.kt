package modeep.hear.domain.chat.port.out.external

import modeep.hear.infrastructure.adapter.out.chat.external.dto.response.InitChatResponse
import java.util.UUID

interface FetchChatPort {
    suspend fun initChat(chatId: UUID): InitChatResponse
}
