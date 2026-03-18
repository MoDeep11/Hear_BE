package modeep.hear.infrastructure.adapter.out.auth.external

import modeep.hear.domain.auth.model.EmailVerification
import modeep.hear.domain.auth.model.PasswordResetTicket
import modeep.hear.domain.auth.port.out.MailExternalPort
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class AuthExternalAdapter(
    private val mailSender: JavaMailSender
) : MailExternalPort {
    private val domain = "https://your-service-frontend.com"

    @Async("mailAsyncExecutor")
    override fun send(request: EmailVerification) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(request.email)
        helper.setSubject("[HEAR] 회원가입 인증번호")
        helper.setText("인증번호: **${request.code}** \n5분 이내에 입력해주세요.", true)

        mailSender.send(message)
    }

    @Async("mailAsyncExecutor")
    override fun send(request: PasswordResetTicket) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(request.email)
        helper.setSubject("[HEAR] 비밀번호 재설정")
        helper.setText(
            """
            <h3>비밀번호 재설정 안내</h3>
            <p>아래 링크를 클릭하여 비밀번호를 재설정해 주세요.</p>
            <a href="$domain/password-reset?ticket=${request.ticket}">비밀번호 재설정하기</a>
            <p>이 링크는 5분 동안만 유효합니다.</p>
        """,
            true
        )

        mailSender.send(message)
    }
}
