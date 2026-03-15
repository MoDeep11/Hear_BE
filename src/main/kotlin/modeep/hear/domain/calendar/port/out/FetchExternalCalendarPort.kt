package modeep.hear.domain.calendar.port.out

import java.time.LocalDate

interface FetchExternalCalendarPort {
    fun fetch(year: Int): Set<LocalDate>
}
