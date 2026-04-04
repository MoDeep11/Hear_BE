package modeep.hear.infrastructure.adapter.out.user.persistence

import modeep.hear.domain.user.model.UserCalendar
import modeep.hear.domain.user.model.id.UserCalendarId
import modeep.hear.domain.user.port.out.UserCalendarPort
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.UserCalendarJpaEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.id.UserCalendarIdEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserCalendarRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

@Component
class UserCalendarPersistenceAdapter(
    private val repo: UserCalendarRepository
) : UserCalendarPort {

    override fun findAllByUserIdAndYearMonth(userId: UUID, yearMonth: YearMonth): List<UserCalendar> {
        val start = yearMonth.atDay(1).atStartOfDay()
        val end = yearMonth.plusMonths(1).atDay(1).atStartOfDay()

        val existing = repo.findAllByIdUserIdAndBaseTimeCreatedAtGreaterThanEqualAndBaseTimeCreatedAtLessThan(
            userId,
            start,
            end
        )

        val existingDates = existing.map { it.id.calendarDate }.toSet()
        val allDates = (1..yearMonth.lengthOfMonth()).map { yearMonth.atDay(it) }
        val missingDates = allDates.filter { it !in existingDates }

        if (missingDates.isNotEmpty()) {
            val newEntities = missingDates.map { date -> createDefaultEntity(date, userId) }
            repo.saveAll(newEntities)
            return (existing + newEntities).map { it.toModel() }
        }

        return existing.map { it.toModel() }
    }

    private fun createDefaultEntity(date: LocalDate, userId: UUID) = UserCalendarJpaEntity(
        id = UserCalendarIdEntity(calendarDate = date, userId = userId)
    )

    private fun UserCalendarJpaEntity.toModel() = UserCalendar(
        id = UserCalendarId(
            calendarDate = id.calendarDate,
            userId = id.userId
        ),
        hasDiary = hasDiary,
        diaryId = diaryId,
        emotion = emotion
    )
}
