package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.model.EmailVerification
import modeep.hear.domain.auth.port.`in`.SendEmailUseCase
import modeep.hear.domain.auth.port.out.EmailVerificationPort
import modeep.hear.domain.auth.port.out.MailExternalPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.util.VerificationCodeGenerator
import modeep.hear.infrastructure.adapter.`in`.auth.dto.SendEmailRequest
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class SendEmailAuthService(
    private val redisTemplate: StringRedisTemplate,  // TODO: 언젠가 변경
    private val codeGenerator: VerificationCodeGenerator,
    private val mailExternalPort: MailExternalPort,
    private val emailVerificationPort: EmailVerificationPort
) : SendEmailUseCase {
    override fun execute(request: SendEmailRequest) {
        val limitKey = "AUTH:LIMIT:${request.email}"

        if (redisTemplate.hasKey(limitKey) == true) {
            throw BusinessException(AuthErrorCode.TOO_MANY_EMAIL_REQUESTS)
        }

        val code = codeGenerator.generate()

        val emailVerification = EmailVerification(
            email = request.email,
            code = code
        )
        emailVerificationPort.save(emailVerification)

        mailExternalPort.send(emailVerification)
        redisTemplate.opsForValue().set(limitKey, "sent", 60, TimeUnit.SECONDS)
    }
}
