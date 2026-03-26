package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.AiImageTask
import modeep.hear.domain.chat.port.`in`.GenerateImageInChatUseCase
import modeep.hear.domain.chat.port.out.CommandAiImageTaskPort
import modeep.hear.domain.chat.port.out.QueryChatPort
import modeep.hear.domain.chat.vo.AiImageTaskStatus
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.GenerateImageInChatRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.GenerateImageInChatResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class GenerateImageInChatService(
    private val securityPort: SecurityPort,
    private val chatPort: QueryChatPort,
    private val commandAiImageTaskPort: CommandAiImageTaskPort
) : GenerateImageInChatUseCase {
    override fun execute(
        chatId: UUID,
        request: GenerateImageInChatRequest
    ): GenerateImageInChatResponse {
        val user = securityPort.getCurrentUser()
        val chat = chatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        chat.validateOwner(user.id)
        if (!request.isReserved) throw BusinessException(ChatErrorCode.INVALID_GENERATION_REQUEST)

        val task = AiImageTask.create(
            sessionId = chatId,
            status = AiImageTaskStatus.RESERVED
        )

        commandAiImageTaskPort.save(task)

        // todo: ai 서버로 요청

        return GenerateImageInChatResponse(
            taskId = task.id.toString()
        )
    }
}
