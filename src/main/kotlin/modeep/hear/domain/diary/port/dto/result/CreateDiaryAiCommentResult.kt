package modeep.hear.domain.diary.port.dto.result

import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.DiaryAiComment
import modeep.hear.global.error.exception.BusinessException
import java.util.UUID

data class CreateDiaryAiCommentResult(
    val diaryId: UUID,
    val aiComment: String
) {
    companion object {
        fun from(diaryAiComment: DiaryAiComment): CreateDiaryAiCommentResult =
            CreateDiaryAiCommentResult(
                diaryId = diaryAiComment.diaryId,
                aiComment = diaryAiComment.content
                    ?: throw BusinessException(
                        DiaryErrorCode.INVALID_VALUE,
                        "AI의 피드백을 찾을 수 없습니다."
                    )
            )
    }
}
