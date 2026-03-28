package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.`in`.CreateChatUseCase
import modeep.hear.domain.chat.port.out.ChatPort
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateChatService(
    private val securityPort: SecurityPort,
    private val chatPort: ChatPort
) : CreateChatUseCase {
    override suspend fun execute(): CreateChatResponse {
        val user = securityPort.getCurrentUser()
        val newChat = Chat.create(user.id)

        chatPort.save(newChat)

        val init = chatPort.initChat(newChat.id)

        return CreateChatResponse(
            chatId = newChat.id,
            initialMessage = init.initialMessage,
            createdAt = newChat.baseTime.createdAt
        )
    }
}
