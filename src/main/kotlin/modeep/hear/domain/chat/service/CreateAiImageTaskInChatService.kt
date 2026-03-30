package modeep.hear.domain.chat.service

import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.AiImageTask
import modeep.hear.domain.chat.port.dto.result.CreateAiImageTaskResult
import modeep.hear.domain.chat.port.`in`.CreateAiImageTaskInChatUseCase
import modeep.hear.domain.chat.port.out.AiImageTaskPort
import modeep.hear.domain.chat.vo.AiImageTaskStatus
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateAiImageTaskRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class CreateAiImageTaskInChatService(
    private val aiImageTaskPort: AiImageTaskPort
) : CreateAiImageTaskInChatUseCase {
    override fun execute(
        chatId: UUID,
        request: CreateAiImageTaskRequest
    ): CreateAiImageTaskResult {
        if (!request.isReserved) throw BusinessException(ChatErrorCode.INVALID_GENERATION_REQUEST)

        val task = AiImageTask.create(
            chatId = chatId,
            status = AiImageTaskStatus.RESERVED
        )

        aiImageTaskPort.save(task)

        return CreateAiImageTaskResult.from(task)
    }
}
