package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.ResetPasswordAuthUseCase
import modeep.hear.domain.auth.port.out.PasswordPort
import modeep.hear.domain.auth.port.out.PasswordResetTicketPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.UserPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ResetPasswordRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ResetPasswordAuthService(
    private val passwordResetTicketPort: PasswordResetTicketPort,
    private val userPort: UserPort,
    private val passwordPort: PasswordPort
) : ResetPasswordAuthUseCase {
    override fun execute(request: ResetPasswordRequest) {
        val ticket = passwordResetTicketPort.findByTicket(request.ticket)
            ?: throw BusinessException(AuthErrorCode.INVALID_TICKET)

        val user = userPort.findByEmail(ticket.email)
            ?: throw BusinessException(
                UserErrorCode.USER_NOT_FOUND
            )

        user.updatePassword(passwordPort.encode(request.password))
        userPort.save(user)

        passwordResetTicketPort.deleteByTicket(ticket.ticket)
    }
}
