package modeep.hear.infrastructure.adapter.out.user.persistence.repository

import modeep.hear.infrastructure.adapter.out.user.persistence.entity.UserStatJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserStatRepository : JpaRepository<UserStatJpaEntity, UUID>