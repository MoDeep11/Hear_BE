package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.`in`.CreateMessageUseCase
import modeep.hear.domain.chat.port.out.ChatPort
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
    private val chatPort: ChatPort
) : CreateMessageUseCase {
    override fun execute(
        chatId: UUID,
        request: CreateMessageRequest
    ): CreateMessageResponse {
        val user = securityPort.getCurrentUser()
        val chat = chatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        chat.validateOwner(user.id)

        val userMessage = Message.create(
            chatId = chatId,
            sender = Sender.USER,
            message = request.message,
            messageType = MessageType.TEXT
        )

        // todo: ai 서버와 소통

        val aiStubMessage = Message.create(
            chatId = chatId,
            sender = Sender.AI,
            message = "AI 답변입니다. todo: ai 답변을 받도록 변경",
            messageType = MessageType.TEXT
        )

        chat.addMessage(userMessage)
        // chat.addMessage(aiStubMessage)
        chatPort.save(chat)

        return CreateMessageResponse(
            chatId = chatId,
            content = aiStubMessage.message,
            createdAt = aiStubMessage.baseTime.createdAt,
            suggestion = null
        )
    }
}
