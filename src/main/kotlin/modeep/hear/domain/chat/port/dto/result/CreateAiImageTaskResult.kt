package modeep.hear.domain.chat.port.dto.result

import modeep.hear.domain.chat.model.AiImageTask
import modeep.hear.domain.chat.vo.AiImageTaskStatus
import java.util.UUID

data class CreateAiImageTaskResult(
    val taskId: UUID,
    val status: AiImageTaskStatus
) {
    companion object {
        fun from(task: AiImageTask): CreateAiImageTaskResult {
            return CreateAiImageTaskResult(
                task.id,
                task.status
            )
        }
    }
}
