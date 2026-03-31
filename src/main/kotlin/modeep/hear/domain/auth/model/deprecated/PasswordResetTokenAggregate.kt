package modeep.hear.domain.auth.model.deprecated

import modeep.hear.domain.auth.vo.ResetToken
import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import java.time.LocalDateTime
import java.util.UUID

@Deprecated(
    "Not used anymore",
    ReplaceWith("PasswordResetTicket")
)
@Aggregate
data class PasswordResetTokenAggregate(
    val id: UUID? = null,
    val userId: UUID? = null,
    val token: ResetToken,
    val isUsed: Boolean = false, // token이 사용되었는지
    val expiresAt: LocalDateTime,
    val version: Long, // 낙관적 락
    val baseTime: BaseTime
) {
    fun isExpired(): Boolean = LocalDateTime.now().isAfter(expiresAt)

    fun use(): PasswordResetTokenAggregate {
        require(!isUsed && !isExpired())
        return this.copy(isUsed = true) // 복사한 객체 반환
    }
}
