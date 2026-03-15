package modeep.hear.domain.calendar.port.out

import modeep.hear.domain.calendar.model.Calendar
import java.time.LocalDate

interface CommandCalendarPort {
    fun saveAll(calendars: List<Calendar>): List<Calendar>
    fun deleteByCalendarDateBetween(start: LocalDate, end: LocalDate)
}
