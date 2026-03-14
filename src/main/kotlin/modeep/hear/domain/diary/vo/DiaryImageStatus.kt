package modeep.hear.domain.diary.vo

import modeep.hear.domain.common.vo.ModelStatus

enum class DiaryImageStatus(
    override val isFinalState: Boolean,
    override val label: String
) : ModelStatus {
    PROCESSING(false, "진행 중"),
    COMPLETED(true, "완료"),
    FAILED(false, "실패")
}
