package modeep.hear.domain.chat.service

import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.AiImageTask
import modeep.hear.domain.chat.port.`in`.GenerateImageInChatUseCase
import modeep.hear.domain.chat.port.out.command.CommandAiImageTaskPort
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
    private val commandAiImageTaskPort: CommandAiImageTaskPort,
    private val checkUserWithChatService: CheckUserWithChatService
) : GenerateImageInChatUseCase {
    override fun execute(
        chatId: UUID,
        request: GenerateImageInChatRequest
    ): GenerateImageInChatResponse {
        checkUserWithChatService.execute(chatId)
        if (!request.isReserved) throw BusinessException(ChatErrorCode.INVALID_GENERATION_REQUEST)

        val task = AiImageTask.create(
            chatId = chatId,
            status = AiImageTaskStatus.RESERVED
        )

        commandAiImageTaskPort.save(task)

        // todo: ai 서버로 요청

        return GenerateImageInChatResponse(
            taskId = task.id.toString()
        )
    }
}
