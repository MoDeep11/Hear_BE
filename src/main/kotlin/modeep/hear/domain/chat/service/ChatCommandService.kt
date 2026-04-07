package modeep.hear.domain.chat.service

import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.out.ChatPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ChatCommandService(
    private val chatPort: ChatPort
) {

    @Transactional
    suspend fun saveChat(
        userId: UUID,
        chat: Chat
    ) {
        chat.validateOwner(userId)
        chatPort.save(chat)
    }

    @Transactional
    suspend fun okChatWithSuspend(
        userId: UUID,
        chat: Chat
    ) {
        chat.validateOwner(userId)

        chat.ok()
        chatPort.save(chat)
    }
}
