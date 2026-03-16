package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.BlacklistToken
import modeep.hear.domain.auth.port.out.BlacklistTokenPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.BlacklistTokenRedisEntity
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.BlacklistRepository
import org.springframework.stereotype.Component

@Component
class BlacklistTokenPersistenceAdapter(
    private val blacklistRepository: BlacklistRepository
) : BlacklistTokenPort {
    override fun save(token: BlacklistToken) {
        blacklistRepository.save(
            BlacklistTokenRedisEntity(
                accessToken = token.accessToken,
                userId = token.userId,
                timeToLive = token.timeToLive
            )
        )
    }

    override fun exists(token: String): Boolean =
        blacklistRepository.existsById(token)
}
