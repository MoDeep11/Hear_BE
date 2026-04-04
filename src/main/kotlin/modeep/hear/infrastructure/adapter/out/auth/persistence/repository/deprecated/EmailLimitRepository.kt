package modeep.hear.infrastructure.adapter.out.auth.persistence.repository.deprecated

import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.deprecated.EmailLimitRedisEntity
import org.springframework.data.repository.CrudRepository

@Deprecated("Use RedisTemplate instead")
interface EmailLimitRepository : CrudRepository<EmailLimitRedisEntity, String>
