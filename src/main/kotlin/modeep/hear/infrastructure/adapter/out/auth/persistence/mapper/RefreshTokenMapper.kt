package modeep.hear.infrastructure.adapter.out.auth.persistence.mapper

import modeep.hear.domain.auth.model.RefreshToken
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.RefreshTokenRedisEntity
import org.springframework.stereotype.Component

@Component
class RefreshTokenMapper {
    fun toDomain(entity: RefreshTokenRedisEntity): RefreshToken {
        return RefreshToken(
            refreshToken = entity.refreshToken,
            timeToLive = entity.timeToLive
        )
    }

    fun toEntity(domain: RefreshToken) = RefreshTokenRedisEntity(
        refreshToken = domain.refreshToken,
        timeToLive = domain.timeToLive
    )
}
