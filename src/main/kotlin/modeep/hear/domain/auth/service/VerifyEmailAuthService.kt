package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.VerifyEmailAuthUseCase
import modeep.hear.domain.auth.port.out.EmailVerificationPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.auth.dto.VerifyEmailRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VerifyEmailAuthService(
    private val emailVerificationPort: EmailVerificationPort,
) : VerifyEmailAuthUseCase {
    override fun execute(request: VerifyEmailRequest) {
        val auth = emailVerificationPort.findByEmail(request.email)
            ?: throw BusinessException(AuthErrorCode.VERIFICATION_TIMEOUT)

        if (auth.code != request.code) {
            throw BusinessException(AuthErrorCode.INVALID_VERIFICATION_CODE)
        }

        val ticket = UUID.randomUUID().toString()
        // verifiedEmailTicketRepository.save(VerifiedEmailTicket(ticketToken, email))

        emailVerificationPort.delete(auth.email)
    }
}
