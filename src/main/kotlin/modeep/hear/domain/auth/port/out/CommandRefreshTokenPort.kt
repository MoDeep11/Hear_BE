package modeep.hear.domain.auth.port.out

import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.RefreshTokenRedisEntity

interface CommandRefreshTokenPort {
    fun save(refreshToken: RefreshTokenRedisEntity)

    fun delete(refreshToken: String)
}
