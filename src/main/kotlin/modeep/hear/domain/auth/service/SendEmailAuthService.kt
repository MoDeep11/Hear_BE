package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.model.EmailVerification
import modeep.hear.domain.auth.port.`in`.SendEmailUseCase
import modeep.hear.domain.auth.port.out.EmailVerificationPort
import modeep.hear.domain.auth.port.out.MailExternalPort
import modeep.hear.global.util.VerificationCodeGenerator
import modeep.hear.infrastructure.adapter.`in`.auth.dto.SendEmailRequest
import org.springframework.stereotype.Service

@Service
class SendEmailAuthService(
    private val codeGenerator: VerificationCodeGenerator,
    private val mailExternalPort: MailExternalPort,
    private val emailVerificationPort: EmailVerificationPort
) : SendEmailUseCase {
    override fun execute(request: SendEmailRequest) {
        val code = codeGenerator.generate()

        val emailVerification = EmailVerification(
            email = request.email,
            code = code
        )
        emailVerificationPort.save(emailVerification)

        mailExternalPort.send(emailVerification)
    }
}
