package modeep.hear.domain.calendar.port.out

import modeep.hear.domain.calendar.model.Calendar
import java.time.LocalDate

interface QueryCalendarPort {
    fun existsByCalendarDateBetween(start: LocalDate, end: LocalDate): Boolean

    fun findByCalendarDateBetween(start: LocalDate, end: LocalDate): List<Calendar>
}