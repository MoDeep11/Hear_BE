package modeep.hear.infrastructure.adapter.out.auth.persistence.repository

import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.EmailVerificationRedisEntity
import org.springframework.data.repository.CrudRepository

interface EmailVerificationRepository : CrudRepository<EmailVerificationRedisEntity, String> {
    fun findByEmail(email: String): EmailVerificationRedisEntity?
}
