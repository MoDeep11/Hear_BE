package modeep.hear.infrastructure.adapter.out.auth.persistence.mapper

import modeep.hear.domain.auth.model.EmailLimit
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.EmailLimitRedisEntity
import org.springframework.stereotype.Component

@Component
class EmailLimitMapper {
    fun toModel(entity: EmailLimitRedisEntity) =
        EmailLimit(
            email = entity.email,
            count = entity.count,
            timeToLive = entity.timeToLive
        )

    fun toEntity(domain: EmailLimit) =
        EmailLimitRedisEntity(
            email = domain.email,
            count = domain.count,
            timeToLive = domain.timeToLive
        )
}
