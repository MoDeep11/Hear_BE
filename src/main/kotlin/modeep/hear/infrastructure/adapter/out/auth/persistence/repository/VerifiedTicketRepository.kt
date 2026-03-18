package modeep.hear.infrastructure.adapter.out.auth.persistence.repository

import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.VerifiedTicketRedisEntity
import org.springframework.data.repository.CrudRepository

interface VerifiedTicketRepository : CrudRepository<VerifiedTicketRedisEntity, String>
