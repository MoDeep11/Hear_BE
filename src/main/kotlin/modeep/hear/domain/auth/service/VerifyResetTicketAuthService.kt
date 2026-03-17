package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.VerifyResetTicketAuthUseCase
import modeep.hear.domain.auth.port.out.PasswordResetTicketPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class VerifyResetTicketAuthService(
    private val passwordResetTicketPort: PasswordResetTicketPort
) : VerifyResetTicketAuthUseCase {

    override fun execute(ticket: String) {
        if (passwordResetTicketPort.existsByTicket(ticket)) {
            throw BusinessException(AuthErrorCode.INVALID_TICKET)
        }
    }
}
