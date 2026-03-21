package modeep.hear.domain.user.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.vo.Role
import modeep.hear.domain.user.vo.UserStatus
import modeep.hear.global.error.exception.BusinessException
import java.util.UUID

@Aggregate
data class User(
    val id: UUID,
    val email: String,
    private var password: String,
    val role: Role,
    val status: UserStatus = UserStatus.ACTIVE,
    val baseTime: BaseTime,
    val isEmailSubscribed: Boolean = false
) {
    companion object {
        fun create(
            email: String,
            password: String,
            role: Role
        ): User {
            if (email.isBlank()) {
                throw BusinessException(
                    UserErrorCode.INVALID_VALUE,
                    "이메일은 비어있을 수 없습니다."
                )
            }

            return User(
                id = UUID.randomUUID(),
                email = email,
                password = password,
                role = role,
                baseTime = BaseTime()
            )
        }
    }

    fun updatePassword(newPassword: String): User {
        this.password = newPassword
        return this
    }

    fun getPassword(): String = this.password
}
