package modeep.hear.infrastructure.adapter.out.user.persistence

import modeep.hear.domain.user.model.UserCalendar
import modeep.hear.domain.user.model.id.UserCalendarId
import modeep.hear.domain.user.port.out.UserCalendarPort
import modeep.hear.infrastructure.adapter.out.calendar.persistence.repository.CalendarRepository
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.UserCalendarJpaEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.id.UserCalendarIdEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserCalendarRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

@Component
class UserCalendarPersistenceAdapter(
    private val repo: UserCalendarRepository,
    private val calendarRepo: CalendarRepository
) : UserCalendarPort {

    override fun findAllByUserIdAndYearMonth(userId: UUID, yearMonth: YearMonth): List<UserCalendar> {
        val start = yearMonth.atDay(1)
        val end = yearMonth.atEndOfMonth()

        val existing = repo.findAllByIdUserIdAndIdCalendarDateBetween(userId, start, end)

        val existingDates = existing.map { it.id.calendarDate }.toSet()
        val allDates = (1..yearMonth.lengthOfMonth()).map { yearMonth.atDay(it) }
        val missingDates = allDates.filter { it !in existingDates }

        if (missingDates.isNotEmpty()) {
            val calendars = calendarRepo.findAllByCalendarDateIn(missingDates)
            val calendarMap = calendars.associateBy { it.calendarDate }

            val newEntities = missingDates.mapNotNull { date ->
                calendarMap[date]?.let { calendar ->
                    UserCalendarJpaEntity(
                        id = UserCalendarIdEntity(calendarDate = date, userId = userId),
                        calendar = calendar
                    )
                }
            }

            repo.saveAll(newEntities)
            return (existing + newEntities).map { it.toModel() }
        }

        return existing.map { it.toModel() }
    }

    override fun findByUserIdAndDate(userId: UUID, date: LocalDate): UserCalendar? {
        return repo.findByIdUserIdAndIdCalendarDate(userId, date)?.toModel()
    }

    override fun save(userCalendar: UserCalendar) {
        val idEntity = UserCalendarIdEntity(
            calendarDate = userCalendar.id.calendarDate,
            userId = userCalendar.id.userId
        )
        val calendar = calendarRepo.findById(userCalendar.id.calendarDate).orElseThrow {
            IllegalStateException("Calendar not found for date: ${userCalendar.id.calendarDate}")
        }
        repo.save(
            UserCalendarJpaEntity(
                id = idEntity,
                calendar = calendar,
                hasDiary = userCalendar.hasDiary,
                diaryId = userCalendar.diaryId,
                emotion = userCalendar.emotion
            )
        )
    }

    override fun saveAll(userCalendars: List<UserCalendar>) {
        val dates = userCalendars.map { it.id.calendarDate }
        val calendarMap = calendarRepo.findAllByCalendarDateIn(dates).associateBy { it.calendarDate }

        val entities = userCalendars.mapNotNull { userCalendar ->
            calendarMap[userCalendar.id.calendarDate]?.let { calendar ->
                UserCalendarJpaEntity(
                    id = UserCalendarIdEntity(
                        calendarDate = userCalendar.id.calendarDate,
                        userId = userCalendar.id.userId
                    ),
                    calendar = calendar,
                    hasDiary = userCalendar.hasDiary,
                    diaryId = userCalendar.diaryId,
                    emotion = userCalendar.emotion
                )
            }
        }

        repo.saveAll(entities)
    }

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
