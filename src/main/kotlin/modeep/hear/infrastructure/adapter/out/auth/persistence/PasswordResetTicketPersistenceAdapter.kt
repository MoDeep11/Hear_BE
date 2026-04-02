package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.PasswordResetTicket
import modeep.hear.domain.auth.port.out.PasswordResetTicketPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.mapper.PasswordResetTicketMapper
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.PasswordResetTicketRepository
import org.springframework.stereotype.Component

@Component
class PasswordResetTicketPersistenceAdapter(
    private val repository: PasswordResetTicketRepository,
    private val mapper: PasswordResetTicketMapper
) : PasswordResetTicketPort {
    // --Query--//
    override fun findByTicket(ticket: String): PasswordResetTicket? {
        return repository.findById(ticket).orElse(null)?.let { mapper.toModel(it) }
    }

    override fun existsByTicket(ticket: String): Boolean =
        repository.existsById(ticket)

    // --Command--//
    override fun save(ticket: PasswordResetTicket) {
        val entity = mapper.toEntity(ticket)
        repository.save(entity)
    }

    override fun deleteByTicket(ticket: String) {
        repository.deleteById(ticket)
    }
}
