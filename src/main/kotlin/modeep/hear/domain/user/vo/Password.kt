package modeep.hear.domain.user.vo

@JvmInline
value class Password(val value: String) {
    override fun toString(): String = "********"
}
