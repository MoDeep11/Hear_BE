package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.VerifiedTicket
import modeep.hear.domain.auth.port.out.VerifiedTicketPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.mapper.VerifiedTicketMapper
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.VerifiedTicketRepository
import org.springframework.stereotype.Component

@Component
class VerifiedTicketPersistenceAdapter(
    private val verifiedTicketMapper: VerifiedTicketMapper,
    private val verifiedTicketRepository: VerifiedTicketRepository
) : VerifiedTicketPort {

    // --Command--//
    override fun save(ticket: VerifiedTicket) {
        verifiedTicketRepository.save(
            verifiedTicketMapper.toEntity(ticket)
        )
    }
}
