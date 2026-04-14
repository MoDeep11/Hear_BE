package modeep.hear.domain.chat.service.dev

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.`in`.CreateMessageUseCase
import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.domain.chat.port.out.query.QueryChatPort
import modeep.hear.domain.chat.service.CheckUserWithChatService
import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.domain.storage.vo.FileData
import modeep.hear.domain.storage.vo.ServiceType
import modeep.hear.domain.user.model.User
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
@Primary
@Profile("dev")
class MockCreateMessageService(
    private val checkUserWithChatService: CheckUserWithChatService,
    private val queryChatPort: QueryChatPort,
    private val storagePort: StoragePort,
    private val messagePort: MessagePort
) : CreateMessageUseCase {
    private val log = KotlinLogging.logger {}
    override suspend fun executeText(
        chatId: UUID,
        request: CreateMessageRequest,
        user: User
    ): CreateMessageResponse {
        log.info { "Creating message for chat $chatId" }
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
            message = "시연용 가짜 데이터 입니다.",
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
        val aiMessage = Message.create(
            chatId = chatId,
            sender = Sender.AI,
            message = "테스트용 가짜 데이터입니다.",
            messageType = type,
            voiceUrl = "url"
        )

        messagePort.save(userMessage)
        messagePort.save(aiMessage)

        return CreateMessageResponse(
            chatId = chatId,
            userContent = userMessage.message,
            aiContent = aiMessage.message,
            aiAudioUrl = aiMessage.voiceUrl,
            status = ChatStatus.FINISH,
            userCreatedAt = userMessage.baseTime.createdAt,
            aiCreatedAt = aiMessage.baseTime.createdAt
        )
    }
}
