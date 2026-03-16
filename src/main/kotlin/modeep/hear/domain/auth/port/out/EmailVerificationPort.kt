package modeep.hear.domain.auth.port.out

import modeep.hear.domain.auth.model.EmailVerification

interface EmailVerificationPort {
    fun save(emailVerification: EmailVerification)

    fun findByEmail(email: String): EmailVerification

    fun delete(email: String)
}
