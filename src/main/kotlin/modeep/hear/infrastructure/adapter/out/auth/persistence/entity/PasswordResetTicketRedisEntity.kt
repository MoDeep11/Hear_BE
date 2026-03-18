package modeep.hear.infrastructure.adapter.out.auth.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(value = "AUTH:PASSWORD_RESET")
data class PasswordResetTicketRedisEntity(
    @Id
    val ticket: String,
    val email: String,
    @TimeToLive
    val timeToLive: Long = 300L
)
