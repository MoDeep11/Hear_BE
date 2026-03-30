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
        if (!status.canTransitionTo(AiImageTaskStatus.PROCESSING)) {
            throw IllegalStateException("Cannot transition from $status to PROCESSING")
        }
        status = AiImageTaskStatus.PROCESSING
    }

    fun complete() {
        if (!status.canTransitionTo(AiImageTaskStatus.COMPLETED)) {
            throw IllegalStateException("Cannot transition from $status to COMPLETED")
        }
        status = AiImageTaskStatus.COMPLETED
    }
}
