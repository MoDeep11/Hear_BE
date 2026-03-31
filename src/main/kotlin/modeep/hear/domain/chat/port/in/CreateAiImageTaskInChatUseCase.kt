package modeep.hear.domain.chat.port.`in`

import modeep.hear.domain.chat.port.dto.result.CreateAiImageTaskResult
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateAiImageTaskRequest
import java.util.UUID

interface CreateAiImageTaskInChatUseCase {
    fun execute(
        chatId: UUID,
        request: CreateAiImageTaskRequest
    ): CreateAiImageTaskResult
}
