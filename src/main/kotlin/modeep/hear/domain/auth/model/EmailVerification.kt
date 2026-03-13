package modeep.hear.domain.auth.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.model.base.BaseTime
import java.util.UUID

@Aggregate
data class EmailVerification(
    val id: UUID? = null,
    val email: String,
    val type: String,
    val isVerified: Boolean,
    val expiresAt: Long,
    val baseTime: BaseTime
)
