package modeep.hear.infrastructure.adapter.out.auth.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(value = "AUTH:BL")
class BlacklistTokenRedisEntity(
    @Id
    val accessToken: String,
    @TimeToLive
    val timeToLive: Long
)
