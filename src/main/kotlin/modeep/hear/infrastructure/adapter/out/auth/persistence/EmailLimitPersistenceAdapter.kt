package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.port.out.EmailLimitPort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class EmailLimitPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : EmailLimitPort {

    override fun saveIfAbsent(email: String): Boolean {
        return redisTemplate.opsForValue()
            .setIfAbsent("AUTH:LIMIT:$email", "1", 60, TimeUnit.SECONDS) ?: false
    }
}
