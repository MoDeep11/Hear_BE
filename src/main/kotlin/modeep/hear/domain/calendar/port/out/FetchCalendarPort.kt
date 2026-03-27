package modeep.hear.domain.calendar.port.out

import java.time.LocalDate

interface FetchCalendarPort {
    fun fetch(year: Int): Set<LocalDate>
}
