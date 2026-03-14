package modeep.hear.domain.chat.type

import modeep.hear.domain.common.model.status.ModelStatus

enum class ChatStatus(
    override val isFinalState: Boolean,
    override val label: String
) : ModelStatus {
    ONGOING(false, "진행 중"),
    COMPLETED(true, "완료"),
    ABANDONED(true, "삭제 예정");

    override fun canTransitionTo(next: ModelStatus): Boolean {
        if (this.isFinalState) return false

        return if (next is ChatStatus) {
            when (this) {
                ONGOING -> true
                else -> false
            }
        } else {
            false
        }
    }
}
