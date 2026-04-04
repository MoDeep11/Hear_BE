package modeep.hear.domain.auth.port.out

import modeep.hear.domain.auth.model.EmailLimit

interface EmailLimitPort {
    fun save(emailLimit: EmailLimit)

    fun findByEmail(email: String): EmailLimit?
}
