package modeep.hear.infrastructure.adapter.out.user.persistence

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.user.model.User
import modeep.hear.domain.user.port.out.UserPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.user.mapper.UserMapper
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserPort {
    override fun findByEmail(email: String): User {
        val userEntity = userRepository.findByEmail(email)
            ?: throw BusinessException(
                AuthErrorCode.EMAIL_NOT_FOUND,
                "email: $email"
            )
        return userMapper.toModel(userEntity)
    }
}
