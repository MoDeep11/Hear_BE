package modeep.hear.infrastructure.adapter.out.auth.external

import modeep.hear.domain.auth.port.out.MailExternalPort
import modeep.hear.infrastructure.adapter.`in`.auth.dto.SendEmailRequest
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component
class AuthExternalAdapter(
    private val mailSender: JavaMailSender
) : MailExternalPort {
    override fun send(request: SendEmailRequest) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(request.email)
        helper.setSubject("[Modeep] 회원가입 인증번호입니다.")
        helper.setText("인증번호: **${request.code}** \n5분 이내에 입력해주세요.", true)

        mailSender.send(message)
    }
}
