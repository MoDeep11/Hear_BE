package modeep.hear.domain.user.port.out

import modeep.hear.domain.user.model.UserCalendar
import modeep.hear.domain.user.model.id.UserCalendarId
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

interface UserCalendarPort {
    fun findAllByUserIdAndYearMonth(userId: UUID, yearMonth: YearMonth): List<UserCalendar>

    fun findByUserIdAndDate(userId: UUID, date: LocalDate): UserCalendar?

    fun save(userCalendar: UserCalendar)

    fun saveAll(userCalendars: List<UserCalendar>)
}
