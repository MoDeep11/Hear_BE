package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.`in`.CreateMessageUseCase
import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.domain.chat.port.out.query.QueryChatPort
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class CreateMessageService(
    private val securityPort: SecurityPort,
    private val messagePort: MessagePort,
    private val queryChatPort: QueryChatPort
) : CreateMessageUseCase {
    override suspend fun execute(
        chatId: UUID,
        request: CreateMessageRequest
    ): CreateMessageResponse {
        val user = securityPort.getCurrentUser()
        val chat = queryChatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        chat.validateOwner(user.id)

        val userMessage = Message.create(
            chatId = chatId,
            sender = Sender.USER,
            message = request.message,
            messageType = MessageType.TEXT
        )

        val aiResult = messagePort.sendMessage(chatId, userMessage)

        messagePort.save(userMessage)
        messagePort.save(aiResult.aiMessage)

        return CreateMessageResponse(
            chatId = chatId,
            content = aiResult.aiMessage.message,
            createdAt = aiResult.aiMessage.baseTime.createdAt,
            suggestion = null
        )
    }
}
