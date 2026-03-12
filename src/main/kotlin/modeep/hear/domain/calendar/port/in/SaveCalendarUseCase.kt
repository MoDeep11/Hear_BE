package modeep.hear.domain.calendar.port.`in`

import modeep.hear.domain.calendar.model.Calendar

interface SaveCalendarUseCase {
    fun execute(year: Int, month: Int): List<Calendar>
}