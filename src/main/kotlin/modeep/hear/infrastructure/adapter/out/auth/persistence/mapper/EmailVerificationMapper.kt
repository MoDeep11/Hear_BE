package modeep.hear.infrastructure.adapter.out.auth.persistence.mapper

import modeep.hear.domain.auth.model.EmailVerification
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.EmailVerificationRedisEntity
import org.springframework.stereotype.Component

@Component
class EmailVerificationMapper {
    fun toModel(entity: EmailVerificationRedisEntity) =
        EmailVerification(
            email = entity.email,
            code = entity.code,
            timeToLive = entity.timeToLive
        )

    fun toEntity(domain: EmailVerification) =
        EmailVerificationRedisEntity(
            email = domain.email,
            code = domain.code,
            timeToLive = domain.timeToLive
        )
}
