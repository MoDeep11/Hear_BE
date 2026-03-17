package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.model.EmailVerification
import modeep.hear.domain.auth.model.PasswordResetTicket
import modeep.hear.domain.auth.port.`in`.SendEmailAuthUseCase
import modeep.hear.domain.auth.port.out.EmailVerificationPort
import modeep.hear.domain.auth.port.out.MailExternalPort
import modeep.hear.domain.auth.port.out.PasswordResetTicketPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.QueryUserPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.util.VerificationCodeGenerator
import modeep.hear.infrastructure.adapter.`in`.auth.dto.EmailRequestType
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.SendEmailRequest
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import java.util.concurrent.TimeUnit

@Service
class SendEmailAuthService(
    private val redisTemplate: StringRedisTemplate, // TODO: 언젠가 변경
    private val codeGenerator: VerificationCodeGenerator,
    private val mailExternalPort: MailExternalPort,
    private val emailVerificationPort: EmailVerificationPort,
    private val queryUserPort: QueryUserPort,
    private val passwordResetTicketPort: PasswordResetTicketPort
) : SendEmailAuthUseCase {

    @Transactional
    override fun execute(request: SendEmailRequest) {
        val limitKey = "AUTH:LIMIT:${request.email}"
        when (request.type) {
            EmailRequestType.PASSWORD_RESET -> sendPasswordResetEmail(request.email)
            EmailRequestType.REGISTER -> sendVerificationEmail(request.email)
        }

        // 어뷰징 방지
        val isFirstRequest = redisTemplate.opsForValue()
            .setIfAbsent(limitKey, "sent", 60, TimeUnit.SECONDS) ?: false
        if (!isFirstRequest) throw BusinessException(AuthErrorCode.TOO_MANY_EMAIL_REQUESTS)
    }

    private fun sendPasswordResetEmail(email: String) {
        if (!queryUserPort.existsByEmail(email)) {
            throw BusinessException(UserErrorCode.EMAIL_NOT_FOUND)
        }

        val ticket = PasswordResetTicket(
            ticket = UUID.randomUUID().toString(),
            email = email,
            timeToLive = 600L
        )
        passwordResetTicketPort.save(ticket)

        mailExternalPort.send(ticket)
    }

    private fun sendVerificationEmail(email: String) {
        if (queryUserPort.existsByEmail(email)) {
            throw BusinessException(AuthErrorCode.EMAIL_ALREADY_EXISTS)
        }
        val code = codeGenerator.generate()

        val emailVerification = EmailVerification(
            email = email,
            code = code
        )
        emailVerificationPort.save(emailVerification)

        mailExternalPort.send(emailVerification)
    }
}
