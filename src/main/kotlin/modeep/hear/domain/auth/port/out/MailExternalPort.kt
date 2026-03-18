package modeep.hear.domain.auth.port.out

import modeep.hear.domain.auth.model.EmailVerification
import modeep.hear.domain.auth.model.PasswordResetTicket

interface MailExternalPort {
    fun send(request: EmailVerification)
    fun send(request: PasswordResetTicket)
}
