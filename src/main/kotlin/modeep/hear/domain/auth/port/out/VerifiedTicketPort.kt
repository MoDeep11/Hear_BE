package modeep.hear.domain.auth.port.out

import modeep.hear.domain.auth.model.VerifiedTicket

interface VerifiedTicketPort {
    fun save(ticket: VerifiedTicket)
}
