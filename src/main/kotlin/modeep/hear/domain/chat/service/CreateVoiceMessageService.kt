package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.`in`.CreateVoiceMessageUseCase
import modeep.hear.domain.chat.port.out.ChatPort
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateVoiceMessageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class CreateVoiceMessageService(
    private val securityPort: SecurityPort,
    private val chatPort: ChatPort
) : CreateVoiceMessageUseCase {
    override fun execute(
        chatId: UUID,
        request: CreateVoiceMessageRequest
    ): CreateVoiceMessageResponse {
        val user = securityPort.getCurrentUser()
        val chat = chatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        chat.validateOwner(user.id)

        val userMessage = Message.create(
            chatId = chatId,
            sender = Sender.USER,
            message = "알 수 없음",
            messageType = MessageType.VOICE,
            voiceUrl = request.voiceUrl,
            duration = request.duration
        )

        // todo: ai 서버와 소통

        userMessage.updateMessage(message = "유저의 음성 STT")

        val aiStubMessage = Message.create(
            chatId = chatId,
            sender = Sender.AI,
            message = "todo: ai 답변 메시지",
            messageType = MessageType.VOICE,
            voiceUrl = "https://example.com/ai-voice.mp3",
            duration = 30000
        )

        chat.addMessage(userMessage)
        // chat.addMessage(aiStubMessage)
        chatPort.save(chat)

        return CreateVoiceMessageResponse(
            chatId = chatId,
            userTranscription = "유저 답변 텍스트",
            aiResponseText = "ai 답변 텍스트",
            aiAudioUrl = aiStubMessage.voiceUrl!!,
            suggestion = null
        )
    }
}
