package modeep.hear.infrastructure.adapter.`in`.chat.dto.response

import modeep.hear.domain.chat.port.dto.result.CreateAiImageTaskResult
import java.util.UUID

data class CreateAiImageTaskResponse(
    val taskId: UUID,
) {
    companion object {
        fun from(res: CreateAiImageTaskResult): CreateAiImageTaskResponse {
            return CreateAiImageTaskResponse(res.taskId)
        }
    }
}