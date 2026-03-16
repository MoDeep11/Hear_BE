package modeep.hear.domain.auth.port.out

import modeep.hear.infrastructure.adapter.`in`.auth.dto.SendEmailRequest

interface MailExternalPort {
    fun send(request: SendEmailRequest)
}
