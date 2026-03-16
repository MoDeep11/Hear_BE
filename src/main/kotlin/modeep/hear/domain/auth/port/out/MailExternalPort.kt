package modeep.hear.domain.auth.port.out

import modeep.hear.domain.auth.model.EmailVerification

interface MailExternalPort {
    fun send(request: EmailVerification)
}
