package modeep.hear.domain.diary.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.diary.vo.DiaryAiCommentStatus
import java.util.UUID

data class DiaryAiComment(
    val diaryId: UUID? = null,
    val content: String? = null,
    val diaryImageStatus: DiaryAiCommentStatus = DiaryAiCommentStatus.PENDING,
    val baseTime: BaseTime
)
