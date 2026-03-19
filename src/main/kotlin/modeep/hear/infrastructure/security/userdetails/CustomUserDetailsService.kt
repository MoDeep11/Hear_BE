package modeep.hear.infrastructure.security.userdetails

import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.QueryUserPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomUserDetailsService(
    private val userPort: QueryUserPort
) : UserDetailsService {
    override fun loadUserByUsername(uesrId: String): UserDetails =
        userPort.findById(UUID.fromString(uesrId))
            ?.let { CustomUserDetails(it) }
            ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
}
