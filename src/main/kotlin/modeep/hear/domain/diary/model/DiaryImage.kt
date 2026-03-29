package modeep.hear.domain.diary.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import java.util.UUID

@Aggregate
data class DiaryImage(
    val id: UUID,
    var diaryId: UUID? = null,
    val imageUrl: String? = null,
    val order: Int = 0,
    val sourceType: DiarySourceType = DiarySourceType.AI_MADE,
    val diaryImageStatus: DiaryImageStatus = DiaryImageStatus.PROCESSING,
    val chatId: UUID? = null,
    val baseTime: BaseTime
) {
    companion object {
        fun create(
            diaryId: UUID? = null,
            imageUrl: String? = null,
            order: Int,
            sourceType: DiarySourceType = DiarySourceType.AI_MADE,
            diaryImageStatus: DiaryImageStatus = DiaryImageStatus.PROCESSING,
            chatId: UUID? = null
        ): DiaryImage {
            return DiaryImage(
                id = UUID.randomUUID(),
                diaryId = diaryId,
                imageUrl = imageUrl,
                order = order,
                sourceType = sourceType,
                diaryImageStatus = diaryImageStatus,
                chatId = chatId,
                baseTime = BaseTime()
            )
        }
    }

    fun updateOrder(order: Int) =
        copy(order = order)

    fun assignDiary(diary: Diary) {
        this.diaryId = diary.id
        if (!diary.diaryImages.contains(this)) {
            diary.addImage(this)
        }
    }
}
