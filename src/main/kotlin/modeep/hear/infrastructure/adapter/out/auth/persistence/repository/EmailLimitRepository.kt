package modeep.hear.infrastructure.adapter.out.auth.persistence.repository

import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.EmailLimitRedisEntity
import org.springframework.data.repository.CrudRepository

interface EmailLimitRepository : CrudRepository<EmailLimitRedisEntity, String>
