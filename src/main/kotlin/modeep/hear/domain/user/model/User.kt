package modeep.hear.domain.user.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.user.vo.Role
import modeep.hear.domain.user.vo.UserStatus
import java.util.UUID

@Aggregate
data class User(
    val id: UUID? = null,
    val email: String,
    val password: String,
    val role: Role,
    val status: UserStatus,
    val baseTime: BaseTime,
    val isEmailSubscribed: Boolean
)
