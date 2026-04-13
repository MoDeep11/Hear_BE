package modeep.hear.domain.chat.service

import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.dto.result.SendMessageResult
import modeep.hear.domain.chat.port.`in`.CreateMessageUseCase
import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.domain.chat.port.out.query.QueryChatPort
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.domain.common.component.GetDataForRequestComponent
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.domain.storage.vo.FileData
import modeep.hear.domain.storage.vo.ServiceType
import modeep.hear.domain.user.model.User
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
@Transactional
class CreateMessageService(
    private val messagePort: MessagePort,
    private val queryChatPort: QueryChatPort,
    private val getData: GetDataForRequestComponent,
    private val checkUserWithChatService: CheckUserWithChatService,
    private val storagePort: StoragePort
) : CreateMessageUseCase {
    override suspend fun executeText(
        chatId: UUID,
        request: CreateMessageRequest,
        user: User
    ): CreateMessageResponse {
        checkUserWithChatService.executeWithSuspend(chatId, user)
        val userMessage = Message.create(
            chatId = chatId,
            sender = Sender.USER,
            message = request.message,
            messageType = MessageType.TEXT
        )

        return createMessage(chatId, userMessage, MessageType.TEXT, user)
    }

    override suspend fun executeVoice(
        chatId: UUID,
        voice: MultipartFile,
        user: User
    ): CreateMessageResponse {
        val chat = queryChatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        chat.validateOwner(user.id)

        val fileData = FileData.create(voice, ServiceType.CHAT, user.id)
        val voiceUrl = storagePort.uploadAudio(voice, fileData)

        val userMessage = Message.create(
            chatId = chatId,
            sender = Sender.USER,
            message = "send voice message",
            messageType = MessageType.VOICE,
            voiceUrl = voiceUrl
        )

        return createMessage(chatId, userMessage, MessageType.VOICE, user)
    }

    private suspend fun createMessage(
        chatId: UUID,
        userMessage: Message,
        type: MessageType,
        user: User
    ): CreateMessageResponse {
        val (his, userInfo) = getData.getUserInfoWithHistories(chatId, user)
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
