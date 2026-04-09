package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.LoginAuthUseCase
import modeep.hear.domain.auth.port.out.JwtPort
import modeep.hear.domain.auth.port.out.PasswordPort
import modeep.hear.domain.user.port.out.query.QueryUserPort
import modeep.hear.domain.user.vo.UserStatus
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LoginRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LoginAuthService(
    private val queryUserPort: QueryUserPort,
    private val jwtPort: JwtPort,
    private val passwordPort: PasswordPort
) : LoginAuthUseCase {
    override fun execute(request: LoginRequest): TokenResponse {
        val user = queryUserPort.findByEmail(request.email)
            ?: throw BusinessException(
                AuthErrorCode.INVALID_LOGIN_CREDENTIALS,
                "email: ${request.email}"
            )

        if (user.status == UserStatus.DELETED) {
            throw BusinessException(AuthErrorCode.USER_IS_DELETED)
        }

        if (!passwordPort.matches(request.password, user.getPassword())) {
            throw BusinessException(
                AuthErrorCode.INVALID_LOGIN_CREDENTIALS
            )
        }

        return jwtPort.createToken(user.id)
    }
}
