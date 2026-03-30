package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.vo.AiImageTaskStatus
import modeep.hear.domain.common.vo.BaseTime
import java.util.UUID

data class AiImageTask(
    val id: UUID,
    val chatId: UUID? = null,
    var diaryId: UUID? = null,
    var status: AiImageTaskStatus = AiImageTaskStatus.RESERVED,
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

    fun assignDiary(diaryId: UUID) {
        this.diaryId = diaryId
    }

    fun process() {
        status.canTransitionTo(this.status)
        status = AiImageTaskStatus.PROCESSING
    }

    fun complete() {
        status.canTransitionTo(this.status)
        status = AiImageTaskStatus.COMPLETED
    }
}
