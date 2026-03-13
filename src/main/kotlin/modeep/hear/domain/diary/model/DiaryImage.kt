package modeep.hear.domain.diary.model

import modeep.hear.domain.common.model.base.BaseTime
import modeep.hear.domain.common.model.status.Status
import modeep.hear.domain.diary.type.DiarySourceType
import java.util.UUID

data class DiaryImage(
    val id: UUID? = null,
    val diaryId: UUID? = null,
    val imageUrl: String? = null,
    val order: Int = 0,
    val sourceType: DiarySourceType? = DiarySourceType.AI_MADE,
    val status: Status = Status.PROCESSING, // PROCESSING, COMPLETED, FAILED
    val baseTime: BaseTime
)
