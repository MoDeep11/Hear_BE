package modeep.hear.infrastructure.adapter.out.auth.persistence.repository

import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.RefreshTokenRedisEntity
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshTokenRedisEntity, String>
