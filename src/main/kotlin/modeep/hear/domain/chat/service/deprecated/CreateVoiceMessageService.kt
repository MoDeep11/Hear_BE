package modeep.hear.domain.chat.service.deprecated

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.port.`in`.CreateVoiceMessageUseCase
import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.domain.chat.port.out.query.QueryChatPort

@Deprecated(
    message = "use CreateMessageService instead",
    replaceWith = ReplaceWith("CreateMessageResponse")
)
class CreateVoiceMessageService(
    private val securityPort: SecurityPort,
    private val messagePort: MessagePort,
    private val queryChatPort: QueryChatPort
) : CreateVoiceMessageUseCase {
//    override suspend fun execute(
//        chatId: UUID,
//        request: CreateVoiceMessageRequest
//    ): CreateVoiceMessageResponse {
//        val user = securityPort.getCurrentUser()
//        val chat = queryChatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
//        chat.validateOwner(user.id)
//
//        val userMessage = Message.create(
//            chatId = chatId,
//            sender = Sender.USER,
//            message = "send voice message",
//            messageType = MessageType.VOICE,
//            voiceUrl = request.voiceUrl,
//            duration = request.duration
//        )
//
//        val aiResult = messagePort.sendMessage(chatId, userMessage)
//        val aiAudioUrl = aiResult.aiMessage.voiceUrl
//            ?: throw BusinessException(GlobalErrorCode.AI_SERVER_ERROR)
//
//        userMessage.updateMessage(message = aiResult.userTranscription)
//
//        messagePort.save(userMessage)
//        messagePort.save(aiResult.aiMessage)
//
//        return CreateVoiceMessageResponse(
//            chatId = chatId,
//            userTranscription = userMessage.message,
//            aiResponseText = aiResult.aiMessage.message,
//            aiAudioUrl = aiAudioUrl,
//            suggestion = aiResult.suggestion
//        )
//    }
}
