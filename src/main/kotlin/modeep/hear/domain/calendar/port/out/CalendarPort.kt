package modeep.hear.domain.calendar.port.out

import modeep.hear.domain.calendar.model.Calendar

interface CalendarPort {
    fun saveAll(calendars: List<Calendar>): List<Calendar>
}