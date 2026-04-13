package modeep.hear.infrastructure.adapter.out.user.event

import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.domain.user.model.UserCalendar
import modeep.hear.domain.user.model.id.UserCalendarId
import modeep.hear.domain.user.port.out.UserCalendarPort
import modeep.hear.infrastructure.adapter.out.user.event.event.CreateUserCalendarEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserCalendarEventAdapter(
    private val userCalendarPort: UserCalendarPort,
    private val queryDiaryPort: QueryDiaryPort
) {
    @EventListener
    fun onNotFound(event: CreateUserCalendarEvent) {
        val diaries = queryDiaryPort.findAllByUserIdAndYearMonth(
            userId = event.userId,
            yearMonth = event.yearMonth
        )

        val calendars = event.dates.map { date ->
            val diary = diaries.find { it.baseTime.createdAt.toLocalDate() == date }
            UserCalendar.create(
                id = UserCalendarId(calendarDate = date, userId = event.userId),
                hasDiary = diary != null,
                diaryId = diary?.id,
                emotion = diary?.emotion
            )
        }

        userCalendarPort.saveAll(calendars)
    }
}
