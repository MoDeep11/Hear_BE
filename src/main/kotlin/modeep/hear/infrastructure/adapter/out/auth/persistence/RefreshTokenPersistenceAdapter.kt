package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.RefreshToken
import modeep.hear.domain.auth.port.out.RefreshTokenPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.mapper.RefreshTokenMapper
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.RefreshTokenRepository
import org.springframework.stereotype.Component

@Component
class RefreshTokenPersistenceAdapter(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val refreshTokenMapper: RefreshTokenMapper
) : RefreshTokenPort {
    override fun save(refreshToken: RefreshToken) {
        refreshTokenRepository.save(
            refreshTokenMapper.toEntity(refreshToken)
        )
    }

    override fun delete(refreshToken: String) =
        refreshTokenRepository.deleteById(refreshToken)
}
