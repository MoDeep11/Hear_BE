package modeep.hear.domain.calendar.port.out

import java.time.LocalDate

interface CalendarPort {
    fun fetchHolidays(year: Int, month: Int): List<SimpleHolidayInfo>
}

data class SimpleHolidayInfo(
    val date: LocalDate,
    val name: String
)