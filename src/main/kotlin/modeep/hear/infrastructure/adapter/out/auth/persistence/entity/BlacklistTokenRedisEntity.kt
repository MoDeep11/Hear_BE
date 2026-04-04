package modeep.hear.infrastructure.adapter.out.auth.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID

@RedisHash(value = "AUTH:BL")
class BlacklistTokenRedisEntity(
    @Id
    val accessToken: String,
    val userId: UUID,
    @TimeToLive
    val timeToLive: Long
)
