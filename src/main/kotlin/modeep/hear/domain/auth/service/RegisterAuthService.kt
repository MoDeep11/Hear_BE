package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.RegisterAuthUseCase
import modeep.hear.domain.auth.port.out.JwtPort
import modeep.hear.domain.auth.port.out.PasswordPort
import modeep.hear.domain.user.model.User
import modeep.hear.domain.user.port.`in`.CreateUserUseCase
import modeep.hear.domain.user.vo.Role
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.RegisterRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterAuthService(
    private val passwordPort: PasswordPort,
    private val jwtPort: JwtPort,
    private val createUserUseCase: CreateUserUseCase
) : RegisterAuthUseCase {
    override fun execute(request: RegisterRequest): TokenResponse {
        matches(request.password, request.confirmPassword)

        val user = User.create(
            email = request.email,
            password = passwordPort.encode(request.password),
            role = Role.USER
        )

        createUserUseCase.execute(user)

        val userId = user.id
        return jwtPort.createToken(userId.toString())
    }

    private fun matches(password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            throw BusinessException(
                AuthErrorCode.PASSWORD_NOT_MATCH
            )
        }
    }
}
