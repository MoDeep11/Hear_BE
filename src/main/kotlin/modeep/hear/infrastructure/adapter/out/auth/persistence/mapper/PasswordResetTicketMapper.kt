package modeep.hear.infrastructure.adapter.out.auth.persistence.mapper

import modeep.hear.domain.auth.model.PasswordResetTicket
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.PasswordResetTicketRedisEntity
import org.springframework.stereotype.Component

@Component
class PasswordResetTicketMapper {
    fun toEntity(domain: PasswordResetTicket): PasswordResetTicketRedisEntity {
        return PasswordResetTicketRedisEntity(
            ticket = domain.ticket,
            email = domain.email,
            timeToLive = domain.timeToLive
        )
    }

    fun toModel(entity: PasswordResetTicketRedisEntity): PasswordResetTicket {
        return PasswordResetTicket(
            ticket = entity.ticket,
            email = entity.email,
            timeToLive = entity.timeToLive
        )
    }
}
