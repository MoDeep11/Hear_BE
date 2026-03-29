package modeep.hear.domain.chat.vo

import modeep.hear.domain.common.vo.ModelStatus

enum class ChatStatus(
    override val isFinalState: Boolean,
    override val label: String
) : ModelStatus {
    READY(false, "외부 서버 준비 중"),
    CONTINUE(false, "진행 중"),
    FINISH(true, "완료");

    override fun canTransitionTo(next: ModelStatus): Boolean {
        if (this.isFinalState) return false

        return if (next is ChatStatus) {
            when (this) {
                FINISH -> false
                else -> true
            }
        } else {
            false
        }
    }
}
