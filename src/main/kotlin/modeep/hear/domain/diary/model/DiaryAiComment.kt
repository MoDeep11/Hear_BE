package modeep.hear.domain.diary.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.diary.vo.DiaryAiCommentStatus
import java.util.UUID

data class DiaryAiComment(
    val diaryId: UUID,
    val content: String? = null,
    val status: DiaryAiCommentStatus = DiaryAiCommentStatus.PENDING,
    val baseTime: BaseTime
) {
    companion object {
        fun create(
            diaryId: UUID,
            content: String? = null,
            status: DiaryAiCommentStatus = DiaryAiCommentStatus.PENDING
        ): DiaryAiComment =
            DiaryAiComment(
                diaryId = diaryId,
                content = content,
                status = status,
                baseTime = BaseTime()
            )
    }
}