package modeep.hear.domain.sticker.vo

import modeep.hear.domain.common.vo.ModelStatus

enum class StickerStatus(
    override val isFinalState: Boolean,
    override val label: String
) : ModelStatus {
    PENDING(false, "요청 대기 중"),
    COMPLETED(true, "완료"),
    FAILED(false, "실패")
}
