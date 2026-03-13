package modeep.hear.domain.calendar.port.out

import modeep.hear.domain.calendar.model.Calendar
import java.time.LocalDate

interface QueryCalendarPort {
    fun countByCalendarDateBetween(start: LocalDate, end: LocalDate): Long
    fun findByCalendarDateBetween(start: LocalDate, end: LocalDate): List<Calendar>
}