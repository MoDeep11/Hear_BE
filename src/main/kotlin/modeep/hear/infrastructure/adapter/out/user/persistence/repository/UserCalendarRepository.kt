package modeep.hear.infrastructure.adapter.out.user.persistence.repository

import modeep.hear.infrastructure.adapter.out.user.persistence.entity.UserCalendarJpaEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.id.UserCalendarIdEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.UUID

interface UserCalendarRepository : JpaRepository<UserCalendarJpaEntity, UserCalendarIdEntity> {

    fun findAllByIdUserIdAndBaseTimeCreatedAtGreaterThanEqualAndBaseTimeCreatedAtLessThan(
        userId: UUID,
        createdAtAfter: LocalDateTime,
        createdAtBefore: LocalDateTime
    ): List<UserCalendarJpaEntity>
}
