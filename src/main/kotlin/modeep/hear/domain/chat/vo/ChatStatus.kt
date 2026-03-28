package modeep.hear.domain.chat.vo

import modeep.hear.domain.common.vo.ModelStatus

enum class ChatStatus(
    override val isFinalState: Boolean,
    override val label: String
) : ModelStatus {
    CONTINUE(false, "진행 중"),
    FINISH(true, "완료");

    override fun canTransitionTo(next: ModelStatus): Boolean {
        if (this.isFinalState) return false

        return if (next is ChatStatus) {
            when (this) {
                CONTINUE -> true
                else -> false
            }
        } else {
            false
        }
    }
}
