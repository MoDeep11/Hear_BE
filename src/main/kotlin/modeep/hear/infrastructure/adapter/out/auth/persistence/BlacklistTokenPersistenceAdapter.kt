package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.BlacklistToken
import modeep.hear.domain.auth.port.out.BlacklistTokenPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.BlacklistTokenRedisEntity
import modeep.hear.infrastructure.adapter.out.auth.persistence.mapper.BlacklistTokenMapper
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.BlacklistRepository
import org.springframework.stereotype.Component

@Component
class BlacklistTokenPersistenceAdapter(
    private val blacklistRepository: BlacklistRepository,
    private val blacklistTokenMapper: BlacklistTokenMapper
) : BlacklistTokenPort {
    override fun save(token: BlacklistToken) {
        blacklistRepository.save(
            blacklistTokenMapper.toEntity(token)
        )
    }

    override fun exists(token: String): Boolean =
        blacklistRepository.existsById(token)
}
