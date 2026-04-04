package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.RegisterAuthUseCase
import modeep.hear.domain.auth.port.out.JwtPort
import modeep.hear.domain.auth.port.out.PasswordPort
import modeep.hear.domain.auth.port.out.VerifiedTicketPort
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
    private val verifiedTicketPort: VerifiedTicketPort,
    private val createUserUseCase: CreateUserUseCase
) : RegisterAuthUseCase {
    override fun execute(request: RegisterRequest): TokenResponse {
        val verifiedTicket = verifiedTicketPort.findByTicket(request.ticket)
            ?: throw BusinessException(AuthErrorCode.INVALID_TICKET)

        if (verifiedTicket.email != request.email) {
            throw BusinessException(AuthErrorCode.INVALID_TICKET)
        }

        matches(request.password, request.confirmPassword)

        val user = User.create(
            email = request.email,
            password = passwordPort.encode(request.password),
            role = Role.USER
        )

        createUserUseCase.execute(user)
        verifiedTicketPort.delete(request.ticket)

        return jwtPort.createToken(user.id)
    }

    private fun matches(password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            throw BusinessException(AuthErrorCode.PASSWORD_NOT_MATCH)
        }
    }
}
