package modeep.hear.domain.diary.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryJpaEntity
import java.util.UUID

data class DiaryImage(
    val id: UUID,
    var diaryId: UUID? = null,
    val imageUrl: String? = null,
    val order: Int = 0,
    val sourceType: DiarySourceType = DiarySourceType.AI_MADE,
    val diaryImageStatus: DiaryImageStatus = DiaryImageStatus.PROCESSING,
    val sessionId: UUID? = null,
    val baseTime: BaseTime
) {
    companion object {
        fun create(
            diaryId: UUID? = null,
            imageUrl: String? = null,
            order: Int,
            sourceType: DiarySourceType = DiarySourceType.AI_MADE,
            diaryImageStatus: DiaryImageStatus = DiaryImageStatus.PROCESSING,
            sessionId: UUID? = null
        ): DiaryImage {
            return DiaryImage(
                id = UUID.randomUUID(),
                diaryId = diaryId,
                imageUrl = imageUrl,
                order = order,
                sourceType = sourceType,
                diaryImageStatus = diaryImageStatus,
                sessionId = sessionId,
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
