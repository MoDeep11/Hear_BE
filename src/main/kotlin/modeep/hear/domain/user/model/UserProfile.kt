package modeep.hear.domain.user.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.user.vo.DefaultProfileImageUrl
import java.util.UUID

data class UserProfile(
    val userId: UUID? = null,
    val nickname: String,
    val profileImageUrl: String,
    val baseTime: BaseTime
) {
    companion object {
        fun create(nickname: String?): UserProfile {
            val userId = UUID.randomUUID()
            return UserProfile(
                userId = userId,
                nickname = nickname ?: ("user" + userId.toString().take(8)),
                profileImageUrl = DefaultProfileImageUrl.random().value,
                baseTime = BaseTime()
            )
        }
    }
}
