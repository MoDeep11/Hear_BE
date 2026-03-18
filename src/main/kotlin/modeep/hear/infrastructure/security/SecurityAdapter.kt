package modeep.hear.infrastructure.security

import modeep.hear.domain.auth.port.out.PasswordPort
import org.springframework.security.crypto.password.PasswordEncoder
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.User
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.user.mapper.UserMapper
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityAdapter(
    private val repo: UserRepository,
    private val mapper: UserMapper,
    private val encoder: PasswordEncoder,
) : SecurityPort, PasswordPort {
    override fun matches(rawPassword: String, encodedPassword: String): Boolean =
        encoder.matches(rawPassword, encodedPassword)

    override fun encode(password: String): String {
        return encoder.encode(password)
    }

    override fun getCurrentUser(): User {
        val email = getEmail() ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
        val entity = repo.findByEmail(email)
            ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
        return mapper.toModel(entity)
    }

    fun getEmail(): String? {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated || auth.name == "anonymousUser") {
            return null
        }
        return auth.name
    }
}
