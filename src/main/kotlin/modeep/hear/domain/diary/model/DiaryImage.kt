package modeep.hear.domain.diary.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import java.util.UUID

data class DiaryImage(
    val id: UUID? = null,
    val diaryId: UUID? = null,
    val imageUrl: String? = null,
    val order: Int = 0,
    val sourceType: DiarySourceType? = DiarySourceType.AI_MADE,
    val diaryImageStatus: DiaryImageStatus = DiaryImageStatus.PROCESSING,
    val baseTime: BaseTime
)
