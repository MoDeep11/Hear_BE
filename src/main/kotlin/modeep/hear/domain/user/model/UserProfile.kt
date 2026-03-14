package modeep.hear.domain.user.model

import modeep.hear.domain.common.vo.BaseTime
import java.util.UUID

data class UserProfile(
    val userId: UUID? = null,
    val nickname: String,
    val profileImageUrl: String,
    val baseTime: BaseTime
)
