package modeep.hear.domain.diary.model

import modeep.hear.domain.common.model.base.BaseTime
import modeep.hear.domain.diary.type.DiaryAiCommentStatus
import java.util.UUID

data class DiaryAiComment(
    val diaryId: UUID? = null,
    val content: String,
    val diaryImageStatus: DiaryAiCommentStatus = DiaryAiCommentStatus.PENDING,
    val baseTime: BaseTime
)