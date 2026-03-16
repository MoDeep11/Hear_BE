package modeep.hear.infrastructure.adapter.out.auth.persistence.repository

import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.RefreshTokenRedisEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RefreshTokenRepository : CrudRepository<RefreshTokenRedisEntity, UUID> {
    fun findByRefreshToken(refreshToken: String): RefreshTokenRedisEntity?

    fun deleteByRefreshToken(refreshToken: String): RefreshTokenRedisEntity?
}
