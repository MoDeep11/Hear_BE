package modeep.hear.domain.calendar.port.`in`

import modeep.hear.domain.calendar.port.`in`.dto.SimpleHolidayInfo

interface CalendarUseCase {
    fun fetchHolidays(year: Int, month: Int): List<SimpleHolidayInfo>
}