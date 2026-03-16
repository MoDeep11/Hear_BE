package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.RefreshToken
import modeep.hear.domain.auth.port.out.RefreshTokenPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.RefreshTokenRedisEntity
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.RefreshTokenRepository
import org.springframework.stereotype.Component

@Component
class RefreshTokenPersistenceAdapter(
    private val refreshTokenRepository: RefreshTokenRepository
) : RefreshTokenPort {
    override fun save(refreshToken: RefreshToken) {
        refreshTokenRepository.save(
            RefreshTokenRedisEntity(
                refreshToken = refreshToken.refreshToken,
                timeToLive = refreshToken.timeToLive
            )
        )
    }

    override fun delete(refreshToken: String) =
        refreshTokenRepository.deleteById(refreshToken)
}
