package modeep.hear.domain.calendar.service

import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.`in`.SaveCalendarUseCase
import modeep.hear.domain.calendar.port.out.CommandCalendarPort
import modeep.hear.domain.calendar.port.out.FetchExternalCalendarPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class SaveCalendarService(
    private val commandCalendarPort: CommandCalendarPort,
    private val fetchExternalCalendarPort: FetchExternalCalendarPort
) : SaveCalendarUseCase {
    @Transactional
    override fun execute(year: Int, month: Int): List<Calendar> {
        val holidays = fetchExternalCalendarPort.fetch(year, month)

        val firstDay = LocalDate.of(year, month, 1)

        val calendars = (0 until firstDay.lengthOfMonth()).map { daysToAdd ->
            val currentDate = firstDay.plusDays(daysToAdd.toLong())

            Calendar.create(
                date = currentDate,
                isHoliday = holidays.contains(currentDate)
            )
        }

        return commandCalendarPort.saveAll(calendars)
    }
}
