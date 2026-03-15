package modeep.hear.domain.calendar.service.component

import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.out.QueryCalendarPort
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class QueryCalendarComponent(
    private val queryCalendarPort: QueryCalendarPort
) {
    fun exist(year: Int, month: Int): Boolean {
        val (start, end) = getMonthRange(year, month)
        return queryCalendarPort.countByCalendarDateBetween(start, end) == (end.dayOfMonth).toLong()
    }

    fun find(year: Int, month: Int): List<Calendar> {
        val (start, end) = getMonthRange(year, month)
        return queryCalendarPort.findByCalendarDateBetween(start, end)
    }

    private fun getMonthRange(year: Int, month: Int): Pair<LocalDate, LocalDate> {
        val start = LocalDate.of(year, month, 1)
        val end = start.withDayOfMonth(start.lengthOfMonth())
        return Pair(start, end)
    }
}