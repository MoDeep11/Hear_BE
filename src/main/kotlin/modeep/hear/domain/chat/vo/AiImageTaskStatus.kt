package modeep.hear.domain.chat.vo

import modeep.hear.domain.common.vo.ModelStatus

enum class AiImageTaskStatus(
    override val isFinalState: Boolean,
    override val label: String
) : ModelStatus {
    RESERVED(false, "예약"),
    PROCESSING(false, "생성 중"),
    COMPLETED(true, "완료"),
    FAILED(false, "실패")
}
