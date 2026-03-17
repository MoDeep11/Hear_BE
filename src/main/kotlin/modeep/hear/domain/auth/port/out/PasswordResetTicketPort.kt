package modeep.hear.domain.auth.port.out

import modeep.hear.domain.auth.model.PasswordResetTicket

interface PasswordResetTicketPort {
    fun save(ticket: PasswordResetTicket)

    fun findByTicket(ticket: String): PasswordResetTicket?

    fun deleteByTicket(ticket: String)
}
