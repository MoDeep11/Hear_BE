package modeep.hear.infrastructure.security.userdetails

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.user.mapper.UserMapper
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw BusinessException(
                AuthErrorCode.EMAIL_NOT_FOUND,
                "email: $email"
            )
        return CustomUserDetails(userMapper.toModel(user))
    }
}
