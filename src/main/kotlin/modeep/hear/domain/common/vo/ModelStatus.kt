package modeep.hear.domain.common.vo

interface ModelStatus {
    val isFinalState: Boolean // 상태가 변화할 수 있는지
    val label: String

    fun canTransitionTo(next: ModelStatus): Boolean {
        return !this.isFinalState
    }

    fun isSameAs(other: ModelStatus): Boolean = this == other
}
