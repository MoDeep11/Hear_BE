package modeep.hear.domain.user.vo

// @JvmInline value: mapper 적용이 안됨
data class Password(val value: String) {
    override fun toString(): String = "********"
}

// bcrypt로 인코딩할 것임으로 해당 객체는 사용하지 않음
