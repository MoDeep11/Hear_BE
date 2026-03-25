package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.`in`.CreateMessageUseCase
import modeep.hear.domain.chat.port.out.ChatPort
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
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
        val chat = chatPort.findById(chatId)
        chat.validateOwner(user.id)

        Message.create(
            sessionId = chatId,
            sender = Sender.USER,
            message = request.message,
            messageType = MessageType.TEXT,
        )

        // todo: ai 서버와 소통

        return CreateMessageResponse(
            sessionId = chatId,
            content = "AI 답변입니다. todo: ai 답변을 받도록 변경",
            createdAt = LocalDateTime.now(), // todo: ai 메시지 createdAt으로 변경
            suggestion = null
        )
    }
}