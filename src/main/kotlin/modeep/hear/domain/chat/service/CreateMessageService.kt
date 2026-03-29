package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.dto.result.SendMessageResult
import modeep.hear.domain.chat.port.`in`.CreateMessageUseCase
import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.domain.chat.port.out.query.QueryChatPort
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.domain.common.component.GetDataForRequestComponent
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class CreateMessageService(
    private val securityPort: SecurityPort,
    private val messagePort: MessagePort,
    private val queryChatPort: QueryChatPort,
    private val getData: GetDataForRequestComponent
) : CreateMessageUseCase {
    override suspend fun executeText(
        chatId: UUID,
        request: CreateMessageRequest
    ): CreateMessageResponse {
        validateOwner(chatId)
        val userMessage = Message.create(
            chatId = chatId,
            sender = Sender.USER,
            message = request.message,
            messageType = MessageType.TEXT
        )

        return createMessage(chatId, userMessage, MessageType.TEXT)
    }

    override suspend fun executeVoice(
        chatId: UUID,
        request: CreateVoiceMessageRequest
    ): CreateMessageResponse {
        validateOwner(chatId)
        val userMessage = Message.create(
            chatId = chatId,
            sender = Sender.USER,
            message = "send voice message",
            messageType = MessageType.VOICE,
            voiceUrl = request.voiceUrl,
            duration = request.duration
        )

        return createMessage(chatId, userMessage, MessageType.VOICE)
    }

    private fun validateOwner(chatId: UUID) {
        val user = securityPort.getCurrentUser()
        val chat = queryChatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        chat.validateOwner(user.id)
    }

    private suspend fun createMessage(
        chatId: UUID,
        userMessage: Message,
        type: MessageType
    ): CreateMessageResponse {
        val (his, userInfo) = getData.getUserInfoWithHistories(chatId)
        val aiResult = messagePort.sendMessage(
            chatId,
            his,
            userInfo,
            userMessage
        )

        validateResult(type, aiResult)

        messagePort.save(userMessage)
        messagePort.save(aiResult.aiMessage)

        return CreateMessageResponse(
            chatId = chatId,
            userContent = userMessage.message,
            aiContent = aiResult.aiMessage.message,
            aiAudioUrl = aiResult.aiMessage.voiceUrl,
            status = aiResult.status,
            suggestion = aiResult.suggestion,
            userCreatedAt = userMessage.baseTime.createdAt,
            aiCreatedAt = aiResult.aiMessage.baseTime.createdAt
        )
    }

    private fun validateResult(type: MessageType, aiResult: SendMessageResult) {
        when (type) {
            MessageType.TEXT -> return
            MessageType.VOICE ->
                if (aiResult.aiMessage.voiceUrl == null) {
                    throw BusinessException(GlobalErrorCode.AI_SERVER_ERROR)
                }
        }
    }
}
