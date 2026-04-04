package modeep.hear.infrastructure.adapter.out.auth.persistence.mapper.deprecated

import modeep.hear.domain.auth.model.EmailLimit
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.deprecated.EmailLimitRedisEntity

@Deprecated("Not used anymore")
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
