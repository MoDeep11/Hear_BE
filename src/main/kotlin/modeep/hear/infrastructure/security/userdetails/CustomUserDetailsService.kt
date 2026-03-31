package modeep.hear.infrastructure.security.userdetails

import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.query.QueryUserPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomUserDetailsService(
    private val userPort: QueryUserPort
) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val userUuid = userId.takeIf { it.isNotBlank() }?.let {
            runCatching { UUID.fromString(it) }.getOrNull()
        } ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)

        return userPort.findById(userUuid)
            ?.let { CustomUserDetails(it) }
            ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
    }
}
