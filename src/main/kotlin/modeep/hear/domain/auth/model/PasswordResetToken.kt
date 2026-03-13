package modeep.hear.domain.auth.model

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.model.base.BaseTime
import modeep.hear.global.error.exception.BusinessException
import java.time.LocalDateTime
import java.util.UUID

@Aggregate
data class PasswordResetToken(
    val id: UUID? = null,
    val userId: UUID? = null,
    val token: String,
    val isUsed: Boolean = false, // token이 사용되었는지
    val expiresAt: LocalDateTime,
    val version: Long, // 낙관적 락
    val baseTime: BaseTime
) {
    fun isExpired(): Boolean = LocalDateTime.now().isAfter(expiresAt)

    fun canBeUsed(): Boolean = !isUsed && !isExpired()

    fun use(): PasswordResetToken {
        if (canBeUsed()) {
            throw BusinessException(AuthErrorCode.PASSWORD_TOKEN_INVALID)
        }
        return this.copy(isUsed = true) // 복사한 객체 반환
    }
}
