package modeep.hear.infrastructure.adapter.out.auth.persistence.mapper

import modeep.hear.domain.auth.model.VerifiedTicket
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.VerifiedTicketRedisEntity
import org.springframework.stereotype.Component

@Component
class VerifiedTicketMapper {
    fun toModel(verifiedTicketRedisEntity: VerifiedTicketRedisEntity) =
        VerifiedTicket(
            ticket = verifiedTicketRedisEntity.ticket,
            email = verifiedTicketRedisEntity.email,
            timeToLive = verifiedTicketRedisEntity.timeToLive
        )

    fun toEntity(verifiedTicket: VerifiedTicket) =
        VerifiedTicketRedisEntity(
            ticket = verifiedTicket.ticket,
            email = verifiedTicket.email,
            timeToLive = verifiedTicket.timeToLive
        )
}
