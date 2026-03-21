package modeep.hear.domain.diary.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import java.util.UUID

data class DiaryImage(
    val id: UUID,
    val diaryId: UUID? = null,
    val imageUrl: String? = null,
    val order: Int = 0,
    val sourceType: DiarySourceType = DiarySourceType.AI_MADE,
    val diaryImageStatus: DiaryImageStatus = DiaryImageStatus.PROCESSING,
    val baseTime: BaseTime
) {
    companion object {
        fun create(
            diaryId: UUID? = null,
            imageUrl: String? = null,
            order: Int,
            sourceType: DiarySourceType = DiarySourceType.AI_MADE,
            diaryImageStatus: DiaryImageStatus =  DiaryImageStatus.PROCESSING,
        ) : DiaryImage {
            return DiaryImage(
                id = UUID.randomUUID(),
                diaryId = diaryId,
                imageUrl = imageUrl,
                order = order,
                sourceType = sourceType,
                diaryImageStatus = diaryImageStatus,
                baseTime = BaseTime(),
            )
        }
    }

    fun updateOrder(
        order: Int
    ) = copy(
        order = order
    )
}
