package modeep.hear.domain.auth.model.deprecated

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.vo.EmailVerificationType
import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.global.error.exception.BusinessException
import java.time.LocalDateTime
import java.util.UUID

@Deprecated(
    "Not used anymore",
    ReplaceWith("EmailVerification")
)
@Aggregate
data class EmailVerificationAggregate(
    val id: UUID? = null,
    val email: String,
    val type: EmailVerificationType,
    val isVerified: Boolean = false,
    val expiresAt: LocalDateTime,
    val version: Long,
    val baseTime: BaseTime
) {
    fun verify(): EmailVerificationAggregate {
        if (isVerified) {
            throw BusinessException(AuthErrorCode.EMAIL_ALREADY_VERIFIED)
        }

        if (LocalDateTime.now().isAfter(expiresAt)) {
            throw BusinessException(AuthErrorCode.VERIFICATION_TIMEOUT)
        }

        return this.copy(isVerified = true)
    }
}
