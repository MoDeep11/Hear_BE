package modeep.hear.domain.calendar.service.component

import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.out.QueryCalendarPort
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Year

@Component
class QueryCalendarComponent(
    private val queryCalendarPort: QueryCalendarPort
) {
    fun exist(year: Int): Boolean {
        val (start, end) = getYearRange(year)
        return queryCalendarPort.countByCalendarDateBetween(start, end) == (Year.of(year).length()).toLong()
    }

    fun find(year: Int): List<Calendar> {
        val (start, end) = getYearRange(year)
        return queryCalendarPort.findByCalendarDateBetween(start, end)
    }

    private fun getYearRange(yearInt: Int): Pair<LocalDate, LocalDate> {
        val year = Year.of(yearInt)
        val start = year.atDay(1)
        val end = year.atDay(year.length())
        return Pair(start, end)
    }
}
