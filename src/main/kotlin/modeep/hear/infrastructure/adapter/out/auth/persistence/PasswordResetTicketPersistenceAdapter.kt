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

    //--Query--//
    override fun findByTicket(ticket: String): PasswordResetTicket? {
        return repository.findByTicket(ticket)?.let { mapper.toModel(it) }
    }

    //--Command--//
    override fun save(ticket: PasswordResetTicket) {
        val entity = mapper.toEntity(ticket)
        repository.save(entity)
    }

    override fun deleteByTicket(ticket: String) {
        repository.deleteById(ticket)
    }
}