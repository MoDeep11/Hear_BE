package modeep.hear.domain.diary.type

import modeep.hear.domain.common.model.status.ModelStatus

enum class DiaryImageStatus(
    override val isFinalState: Boolean,
    override val label: String
) : ModelStatus {
    PROCESSING(false, "진행 중"),
    COMPLETED(true, "완료"),
    FAILED(false, "실패")
}