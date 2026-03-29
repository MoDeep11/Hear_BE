package modeep.hear.domain.chat.service

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.`in`.CreateChatUseCase
import modeep.hear.domain.chat.port.out.external.FetchChatPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class CreateChatService(
    private val securityPort: SecurityPort,
    private val chatPort: FetchChatPort,
    private val chatCommandService: ChatCommandService
) : CreateChatUseCase {
    override suspend fun execute(): CreateChatResponse {
        val userId = securityPort.getCurrentUserId()
        val newChat = Chat.create(userId)

        val initResult = try {
            chatPort.initChat(newChat.id)
        } catch (e: Exception) {
            log.error(e) { "AI Server initialization failed for chatId: ${newChat.id}" }
            throw BusinessException(GlobalErrorCode.AI_SERVER_ERROR)
        }

        return chatCommandService.saveChatWithSuspend(userId, newChat, initResult)
    }
}
