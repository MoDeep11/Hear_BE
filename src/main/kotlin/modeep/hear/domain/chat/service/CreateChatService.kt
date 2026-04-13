package modeep.hear.domain.chat.service

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.`in`.CreateChatUseCase
import modeep.hear.domain.chat.port.out.external.FetchChatPort
import modeep.hear.domain.common.component.GetDataForRequestComponent
import modeep.hear.domain.user.model.User
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import org.springframework.stereotype.Service

@Service
class CreateChatService(
    private val chatPort: FetchChatPort,
    private val chatCommandService: ChatCommandService,
    private val getData: GetDataForRequestComponent
) : CreateChatUseCase {
    private val log = KotlinLogging.logger {}

    override suspend fun execute(
        user: User
    ): CreateChatResponse {
        log.info { "Create chat for user: ${user.id}" }
        val userId = user.id
        val newChat = Chat.create(userId)

        chatCommandService.saveChat(userId, newChat)

        val initResult = try {
            chatPort.initChat(newChat.id, getData.getUserInfoOnly(user))
        } catch (e: Exception) {
            log.error(e) { "AI Server initialization failed for chatId: ${newChat.id}" }
            throw BusinessException(GlobalErrorCode.AI_SERVER_ERROR)
        }

        chatCommandService.okChatWithSuspend(userId, newChat)

        return CreateChatResponse(
            chatId = newChat.id,
            initialMessage = initResult.initialMessage,
            createdAt = newChat.baseTime.createdAt
        )
    }
}
