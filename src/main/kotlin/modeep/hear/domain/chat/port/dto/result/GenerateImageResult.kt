package modeep.hear.domain.chat.port.dto.result

import modeep.hear.domain.chat.vo.AiImageTaskStatus
import java.util.UUID

data class GenerateImageResult(
    val taskId: UUID,
    val status: AiImageTaskStatus,
    val message: String
)