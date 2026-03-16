package modeep.hear.domain.auth.model

data class EmailVerification(
    val email: String,
    val code: String,
    val timeToLive: Long = 300L
)

// @Aggregate
// data class EmailVerification(
//    val id: UUID? = null,
//    val email: String,
//    val type: EmailVerificationType,
//    val isVerified: Boolean = false,
//    val expiresAt: LocalDateTime,
//    val version: Long,
//    val baseTime: BaseTime
// ) {
//    fun verify(): EmailVerification {
//        if (isVerified) {
//            throw BusinessException(AuthErrorCode.EMAIL_ALREADY_VERIFIED)
//        }
//
//        if (LocalDateTime.now().isAfter(expiresAt)) {
//            throw BusinessException(AuthErrorCode.VERIFICATION_TIMEOUT)
//        }
//
//        return this.copy(isVerified = true)
//    }
// }
