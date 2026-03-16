package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.model.VerifiedTicket
import modeep.hear.domain.auth.port.`in`.VerifyEmailAuthUseCase
import modeep.hear.domain.auth.port.out.EmailVerificationPort
import modeep.hear.domain.auth.port.out.VerifiedTicketPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.auth.dto.VerifyEmailRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VerifyEmailAuthService(
    private val emailVerificationPort: EmailVerificationPort,
    private val verifiedTicketPort: VerifiedTicketPort
) : VerifyEmailAuthUseCase {
    override fun execute(request: VerifyEmailRequest) {
        val auth = emailVerificationPort.findByEmail(request.email)

        if (auth.code != request.code) {
            throw BusinessException(AuthErrorCode.INVALID_VERIFICATION_CODE)
        }

        val ticket = UUID.randomUUID().toString()
        verifiedTicketPort.save(VerifiedTicket(ticket, request.email))

        emailVerificationPort.delete(auth.email)
    }
}
