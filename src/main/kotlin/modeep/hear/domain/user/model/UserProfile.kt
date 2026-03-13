package modeep.hear.domain.user.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.model.base.BaseTime
import java.util.UUID

@Aggregate
data class UserProfile(
    val userId: UUID? = null,
    val nickname: String,
    val profileImageUrl: String,
    val baseTime: BaseTime
)
