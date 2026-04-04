package modeep.hear.infrastructure.adapter.out.user.persistence.repository

import modeep.hear.infrastructure.adapter.out.user.persistence.entity.UserCalendarJpaEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.id.UserCalendarIdEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface UserCalendarRepository : JpaRepository<UserCalendarJpaEntity, UserCalendarIdEntity> {
    fun findAllByIdUserIdAndIdCalendarDateBetween(
        idUserId: UUID,
        idCalendarDateAfter: LocalDate,
        idCalendarDateBefore: LocalDate
    ): MutableList<UserCalendarJpaEntity>

    fun findByIdUserIdAndIdCalendarDate(idUserId: UUID, idCalendarDate: LocalDate): UserCalendarJpaEntity?
}
