package modeep.hear.domain.user.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.user.vo.Password
import modeep.hear.domain.user.vo.Role
import modeep.hear.domain.user.vo.UserStatus
import java.util.UUID

@Aggregate
data class User(
    val id: UUID? = null,
    val email: String,
    val password: Password,
    val role: Role,
    val status: UserStatus = UserStatus.ACTIVE,
    val baseTime: BaseTime,
    val isEmailSubscribed: Boolean
)
