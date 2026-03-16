package modeep.hear.infrastructure.adapter.out.auth.persistence.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(value = "AUTH:BL")
class BlacklistTokenRedisEntity(
    @Id
    val accessToken: String,

    val userId: String,

    @TimeToLive
    val timeToLive: Long
)
