package modeep.hear.infrastructure.security

import modeep.hear.domain.auth.port.out.PasswordPort
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.model.User
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.security.userdetails.CustomUserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SecurityAdapter(
    private val encoder: PasswordEncoder
) : SecurityPort, PasswordPort {
    override fun matches(rawPassword: String, encodedPassword: String): Boolean =
        encoder.matches(rawPassword, encodedPassword)

    override fun encode(password: String): String =
        encoder.encode(password)

    override fun getCurrentUser(): User {
        val principal = SecurityContextHolder.getContext().authentication?.principal
        if (principal !is CustomUserDetails) {
            throw BusinessException(GlobalErrorCode.UNAUTHORIZED)
        }
        return principal.getUser()
    }

    override fun getCurrentUserId(): UUID {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated || auth.name == "anonymousUser" || auth.name.isBlank()) {
            throw BusinessException(GlobalErrorCode.UNAUTHORIZED)
        }

        return runCatching { UUID.fromString(auth.name) }
            .getOrElse { throw BusinessException(GlobalErrorCode.UNAUTHORIZED) }
    }
}
