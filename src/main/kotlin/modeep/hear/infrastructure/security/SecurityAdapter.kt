package modeep.hear.infrastructure.security

import modeep.hear.domain.auth.port.out.PasswordPort
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.User
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.user.persistence.mapper.UserMapper
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SecurityAdapter(
    private val repo: UserRepository,
    private val mapper: UserMapper,
    private val encoder: PasswordEncoder
) : SecurityPort, PasswordPort {
    override fun matches(rawPassword: String, encodedPassword: String): Boolean =
        encoder.matches(rawPassword, encodedPassword)

    override fun encode(password: String): String {
        return encoder.encode(password)
    }

    override fun getCurrentUser(): User {
        val userId = getCurrentUserId()

        val entity = repo.findByIdOrNull(userId)
            ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
        return mapper.toModel(entity)
    }

    override fun getCurrentUserId(): UUID {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated || auth.name == "anonymousUser" || auth.name.isBlank()) {
            throw BusinessException(UserErrorCode.USER_NOT_FOUND)
        }

        return runCatching { UUID.fromString(auth.name) }
            .getOrElse { throw BusinessException(UserErrorCode.USER_NOT_FOUND) }
    }
}
