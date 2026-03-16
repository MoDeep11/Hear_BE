package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.RegisterAuthUseCase
import modeep.hear.domain.user.model.User
import modeep.hear.domain.user.port.`in`.CreateUserUseCase
import modeep.hear.domain.user.vo.Role
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.RegisterRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegisterAuthService(
    private val passwordEncoder: PasswordEncoder,
    private val createUserUseCase: CreateUserUseCase
) : RegisterAuthUseCase {
    override fun execute(request: RegisterRequest) {
        if (request.password == request.confirmPassword) {
            throw BusinessException(
                AuthErrorCode.PASSWORD_NOT_MATCH
            )
        }

        val user = User.create(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.USER
        )

        createUserUseCase.execute(user)
    }
}
