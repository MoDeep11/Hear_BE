package modeep.hear.domain.chat.service

import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.out.ChatPort
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import modeep.hear.infrastructure.adapter.out.chat.external.dto.response.InitChatResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ChatCommandService(
    private val chatPort: ChatPort
) {
    @Transactional
    suspend fun saveChatWithSuspend(
        userId: UUID,
        chat: Chat
    ) {
        chat.validateOwner(userId)

        chat.okChat()
        chatPort.save(chat)
    }
}
