package modeep.hear.infrastructure.adapter.out.auth.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(value = "AUTH:LIMIT")
class EmailLimitRedisEntity(
    @Id
    val email: String,

    val count: Int,

    @TimeToLive
    val timeToLive: Long = 60L
)
