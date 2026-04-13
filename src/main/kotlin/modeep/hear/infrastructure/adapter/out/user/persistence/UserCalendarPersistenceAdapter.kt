package modeep.hear.infrastructure.adapter.out.user.persistence

import modeep.hear.domain.common.event.EventPublisher
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.UserCalendar
import modeep.hear.domain.user.model.id.UserCalendarId
import modeep.hear.domain.user.port.out.UserCalendarPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.calendar.persistence.repository.CalendarRepository
import modeep.hear.infrastructure.adapter.out.user.event.event.CreateUserCalendarEvent
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.UserCalendarJpaEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.id.UserCalendarIdEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserCalendarRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

private const val BATCH_SIZE = 100

@Component
class UserCalendarPersistenceAdapter(
    private val repo: UserCalendarRepository,
    private val calendarRepo: CalendarRepository,
    private val eventPublisher: EventPublisher
) : UserCalendarPort {

    override fun findAllByUserIdAndYearMonth(userId: UUID, yearMonth: YearMonth): List<UserCalendar> {
        val start = yearMonth.atDay(1)
        val end = yearMonth.atEndOfMonth()

        val existing = repo.findAllByIdUserIdAndIdCalendarDateBetween(userId, start, end)
            .associateBy { it.id.calendarDate }

        val missing = (1..yearMonth.lengthOfMonth())
            .map { day -> yearMonth.atDay(day) }
            .filter { date -> existing[date] == null }

        if (missing.isNotEmpty()) {
            eventPublisher.publish(
                CreateUserCalendarEvent(
                    userId = userId,
                    dates = missing,
                    yearMonth = yearMonth
                )
            )
        }

        return (1..yearMonth.lengthOfMonth()).map { day ->
            val date = yearMonth.atDay(day)
            existing[date]?.toModel() ?: UserCalendar(
                id = UserCalendarId(calendarDate = date, userId = userId)
            )
        }
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

        val missingDates = dates.filterNot(calendarMap::containsKey)
        if (missingDates.isNotEmpty()) {
            throw BusinessException(UserErrorCode.CALENDAR_NOT_FOUND, "missingDates: $missingDates")
        }

        val entities = userCalendars.map { userCalendar ->
            UserCalendarJpaEntity(
                id = UserCalendarIdEntity(
                    calendarDate = userCalendar.id.calendarDate,
                    userId = userCalendar.id.userId
                ),
                calendar = calendarMap.getValue(userCalendar.id.calendarDate),
                hasDiary = userCalendar.hasDiary,
                diaryId = userCalendar.diaryId,
                emotion = userCalendar.emotion
            )
        }

        repo.saveAll(entities)
    }

    override fun saveAllForAllUsers(userIds: List<UUID>, yearMonth: YearMonth) {
        val dates = (1..yearMonth.lengthOfMonth()).map { yearMonth.atDay(it) }
        val calendarMap = calendarRepo.findAllByCalendarDateIn(dates).associateBy { it.calendarDate }

        val missingDates = dates.filterNot(calendarMap::containsKey)
        if (missingDates.isNotEmpty()) {
            throw BusinessException(UserErrorCode.CALENDAR_NOT_FOUND, "missingDates: $missingDates")
        }

        userIds.chunked(BATCH_SIZE) { chunk ->
            val entities = chunk.flatMap { userId ->
                dates.map { date ->
                    UserCalendarJpaEntity(
                        id = UserCalendarIdEntity(calendarDate = date, userId = userId),
                        calendar = calendarMap.getValue(date)
                    )
                }
            }
            repo.saveAll(entities)
        }
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
