package modeep.hear.domain.user.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.vo.DefaultProfileImageUrl
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.util.checkBlank
import java.util.UUID

data class UserProfile(
    val userId: UUID? = null,
    val nickname: String,
    val profileImageUrl: String,
    val baseTime: BaseTime
) {
    init {
        nickname.checkBlank("nickname")
        if (nickname.length > 20) {
            throw BusinessException(
                UserErrorCode.INVALID_VALUE,
                "nickname 은 20자를 초과할 수 없습니다."
            )
        }
    }
    companion object {
        fun create(nickname: String?): UserProfile {
            val userId = UUID.randomUUID()
            return UserProfile(
                userId = userId,
                nickname = nickname
                    ?.trim()
                    ?.takeUnless { it.isEmpty() }
                    ?: "user${userId.toString().take(8)}",
                profileImageUrl = DefaultProfileImageUrl.random().value,
                baseTime = BaseTime()
            )
        }
    }
}
