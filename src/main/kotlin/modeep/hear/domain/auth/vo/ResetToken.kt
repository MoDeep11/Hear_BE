package modeep.hear.domain.auth.vo

@JvmInline  // 컴파일 시 Primitive 타입으로 취급. 즉 String 취급
value class ResetToken(val value: String) {
    override fun toString(): String = "********"  // 마스킹 처리
}