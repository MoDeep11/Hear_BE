package modeep.hear.domain.diary.model

import modeep.hear.domain.common.model.base.BaseTime
import modeep.hear.domain.common.model.status.Status
import java.util.UUID

data class DiaryAiComment(
    val diaryId: UUID? = null,
    val content: String,
    val status: Status = Status.PENDING,  // EMPTY, PENDING, COMPLETED, FAILED
    val baseTime: BaseTime
)