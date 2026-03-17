package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.RefreshToken
import modeep.hear.domain.auth.port.out.RefreshTokenPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.mapper.RefreshTokenMapper
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.RefreshTokenRepository
import org.springframework.stereotype.Component

@Component
class RefreshTokenPersistenceAdapter(
    private val repo: RefreshTokenRepository,
    private val mapper: RefreshTokenMapper
) : RefreshTokenPort {
    // --Query--//
    override fun findByRefreshToken(refreshToken: String): RefreshToken? {
        return repo.findByRefreshToken(refreshToken)?.let { mapper.toModel(it) }
    }

    override fun existsByRefreshToken(refreshToken: String): Boolean =
        repo.existsById(refreshToken)

    // --Command--//
    override fun save(refreshToken: RefreshToken) {
        repo.save(
            mapper.toEntity(refreshToken)
        )
    }

    override fun delete(refreshToken: String) =
        repo.deleteById(refreshToken)
}
