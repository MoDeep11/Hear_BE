package modeep.hear.domain.auth.port.out

import modeep.hear.domain.auth.model.PasswordResetTicket

interface PasswordResetTicketPort {
    fun findByTicket(ticket: String): PasswordResetTicket?

    fun existsByTicket(ticket: String): Boolean

    fun save(ticket: PasswordResetTicket)

    fun deleteByTicket(ticket: String)
}
