package modeep.hear.infrastructure.adapter.out.auth.persistence.mapper

import modeep.hear.domain.auth.model.RefreshToken
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.RefreshTokenRedisEntity
import org.springframework.stereotype.Component

@Component
class RefreshTokenMapper {
    fun toModel(entity: RefreshTokenRedisEntity): RefreshToken {
        return RefreshToken(
            refreshToken = entity.refreshToken,
            userId = entity.userId,
            timeToLive = entity.timeToLive
        )
    }

    fun toEntity(domain: RefreshToken) = RefreshTokenRedisEntity(
        refreshToken = domain.refreshToken,
        userId = domain.userId,
        timeToLive = domain.timeToLive
    )
}
