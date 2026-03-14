package modeep.hear.domain.diary.type

import modeep.hear.domain.common.model.status.ModelStatus

enum class DiaryAiCommentStatus(
    override val isFinalState: Boolean,
    override val label: String
) : ModelStatus {
    EMPTY(false, "피드백 없음"),
    PENDING(false, "요청 대기 중"),
    COMPLETED(true, "완료"),
    FAILED(false, "실패")
}
