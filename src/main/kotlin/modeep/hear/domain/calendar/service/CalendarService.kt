package modeep.hear.domain.calendar.service

import modeep.hear.domain.calendar.port.out.CalendarPort
import modeep.hear.domain.calendar.model.Calendar
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CalendarService(
    private val calendarPort: CalendarPort,
) {
    fun getProcessedHolidays(year: Int, month: Int): List<Calendar> {
        val holidayItems = calendarPort.fetchHolidays(year, month)

        val holidayDates = holidayItems.map { it.date }.toSet()

        val start = LocalDate.of(year, month, 1)

        return (0 until start.lengthOfMonth()).map { i ->
            val currentDate = start.plusDays(i.toLong())

            Calendar.create(
                date = currentDate,
                isHoliday = holidayDates.contains(currentDate)
            )
        }
    }
}
