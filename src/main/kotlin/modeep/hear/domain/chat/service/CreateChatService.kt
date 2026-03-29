package modeep.hear.domain.chat.service

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.`in`.CreateChatUseCase
import modeep.hear.domain.chat.port.out.ChatPort
import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class CreateChatService(
    private val securityPort: SecurityPort,
    private val chatPort: ChatPort,
) : CreateChatUseCase {
    override suspend fun execute(): CreateChatResponse {
        val user = securityPort.getCurrentUser()
        val newChat = Chat.create(user.id)
        chatPort.save(newChat)

        return try {
            val init = chatPort.initChat(newChat.id)

            newChat.okChat()
            chatPort.save(newChat)

            CreateChatResponse(
                chatId = newChat.id,
                initialMessage = init.initialMessage,
                createdAt = newChat.baseTime.createdAt
            )
        } catch (e: Exception) {
            if (newChat.status == ChatStatus.READY) {
                runCatching { chatPort.delete(newChat.id) }
                    .onFailure { log.error { "Failed to rollback chat entity: ${newChat.id}" } }
            }
            throw BusinessException(GlobalErrorCode.AI_SERVER_ERROR)
        }
    }
}
