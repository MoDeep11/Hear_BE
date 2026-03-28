package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.`in`.CreateVoiceMessageUseCase
import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.domain.chat.port.out.query.QueryChatPort
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateVoiceMessageResponse
import java.util.UUID

@Deprecated("use CreateMessageService instead")
class CreateVoiceMessageService(
    private val securityPort: SecurityPort,
    private val messagePort: MessagePort,
    private val queryChatPort: QueryChatPort
) : CreateVoiceMessageUseCase {
    override suspend fun execute(
        chatId: UUID,
        request: CreateVoiceMessageRequest
    ): CreateVoiceMessageResponse {
        val user = securityPort.getCurrentUser()
        val chat = queryChatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        chat.validateOwner(user.id)

        val userMessage = Message.create(
            chatId = chatId,
            sender = Sender.USER,
            message = "send voice message",
            messageType = MessageType.VOICE,
            voiceUrl = request.voiceUrl,
            duration = request.duration
        )

        val aiResult = messagePort.sendMessage(chatId, userMessage)

        userMessage.updateMessage(message = aiResult.userTranscription)

        messagePort.save(userMessage)
        messagePort.save(aiResult.aiMessage)

        if (aiResult.suggestion != null) {
        }

        return CreateVoiceMessageResponse(
            chatId = chatId,
            userTranscription = userMessage.message,
            aiResponseText = aiResult.aiMessage.message,
            aiAudioUrl = aiResult.aiMessage.voiceUrl ?: throw BusinessException(GlobalErrorCode.AI_SERVER_ERROR),
            suggestion = aiResult.suggestion
        )
    }
}
