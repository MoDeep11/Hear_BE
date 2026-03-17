package modeep.hear.infrastructure.adapter.out.auth.persistence.repository

import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.PasswordResetTicketRedisEntity
import org.springframework.data.repository.CrudRepository

interface PasswordResetTicketRepository : CrudRepository<PasswordResetTicketRedisEntity, String> {
    fun findByTicket(ticket: String): PasswordResetTicketRedisEntity?
}