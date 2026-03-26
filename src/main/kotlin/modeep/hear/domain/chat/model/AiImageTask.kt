package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.vo.AiImageTaskStatus
import modeep.hear.domain.common.vo.BaseTime
import java.util.UUID

data class AiImageTask(
    val id: UUID,
    val chatId: UUID? = null,
    val diaryId: UUID? = null,
    val status: AiImageTaskStatus = AiImageTaskStatus.RESERVED,
    val baseTime: BaseTime
) {
    companion object {
        fun create(
            chatId: UUID? = null,
            diaryId: UUID? = null,
            status: AiImageTaskStatus = AiImageTaskStatus.RESERVED
        ): AiImageTask =
            AiImageTask(
                id = UUID.randomUUID(),
                chatId = chatId,
                diaryId = diaryId,
                status = status,
                baseTime = BaseTime()
            )
    }
}
