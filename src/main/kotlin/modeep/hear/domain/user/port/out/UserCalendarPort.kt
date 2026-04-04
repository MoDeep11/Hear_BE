package modeep.hear.domain.user.port.out

import modeep.hear.domain.user.model.UserCalendar
import java.time.YearMonth
import java.util.UUID

interface UserCalendarPort {
    fun findAllByUserIdAndYearMonth(userId: UUID, yearMonth: YearMonth): List<UserCalendar>
}
