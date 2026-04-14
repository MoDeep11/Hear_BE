package modeep.hear.domain.chat.service.dev

import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.`in`.CreateChatUseCase
import modeep.hear.domain.chat.service.ChatCommandService
import modeep.hear.domain.user.model.User
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Primary
@Profile("dev")
class MockCreateChatService(
    private val chatCommandService: ChatCommandService
) : CreateChatUseCase {
    override suspend fun execute(user: User): CreateChatResponse {
        val userId = user.id
        val newChat = Chat.create(userId)

        chatCommandService.saveChat(userId, newChat)
        chatCommandService.okChatWithSuspend(userId, newChat)

        return CreateChatResponse(
            chatId = newChat.id,
            initialMessage = "시연용 데이터입니다!",
            createdAt = newChat.baseTime.createdAt
        )
    }
}
