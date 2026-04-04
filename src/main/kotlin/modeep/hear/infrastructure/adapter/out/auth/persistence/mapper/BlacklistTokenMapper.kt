package modeep.hear.infrastructure.adapter.out.auth.persistence.mapper

import modeep.hear.domain.auth.model.BlacklistToken
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.BlacklistTokenRedisEntity
import org.springframework.stereotype.Component

@Component
class BlacklistTokenMapper {
    fun toModel(entity: BlacklistTokenRedisEntity) =
        BlacklistToken(
            accessToken = entity.accessToken,
            userId = entity.userId,
            timeToLive = entity.timeToLive
        )

    fun toEntity(domain: BlacklistToken) =
        BlacklistTokenRedisEntity(
            accessToken = domain.accessToken,
            userId = domain.userId,
            timeToLive = domain.timeToLive
        )
}
