package modeep.hear.domain.calendar.port.`in`

import modeep.hear.domain.calendar.model.Calendar
import java.time.LocalDate

interface SaveCalendarUseCase {
    fun execute(year: Int, month: Int, holidays: Set<LocalDate>): List<Calendar>
}