package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.port.out.RefreshTokenPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.RefreshTokenRepository
import org.springframework.stereotype.Component

@Component
class RefreshTokenPersistenceAdapter(
    private val refreshTokenRepository: RefreshTokenRepository
) : RefreshTokenPort {
    override fun save(refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun delete(refreshToken: String) {
        TODO("Not yet implemented")
    }

}
