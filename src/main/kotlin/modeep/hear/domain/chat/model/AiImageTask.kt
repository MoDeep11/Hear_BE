package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.vo.AiImageTaskStatus
import modeep.hear.domain.common.vo.BaseTime
import java.util.UUID

data class AiImageTask(
    val id: UUID? = null,
    val sessionId: UUID? = null,
    val diaryId: UUID? = null,
    val status: AiImageTaskStatus = AiImageTaskStatus.RESERVED,
    val baseTime: BaseTime
)
