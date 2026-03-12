package modeep.hear.domain.calendar.service

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.`in`.SaveCalendarUseCase
import modeep.hear.domain.calendar.port.out.CommandCalendarPort
import modeep.hear.domain.calendar.port.out.FetchExternalCalendarPort
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

private val log = KotlinLogging.logger {}

@Service
class SaveCalendarService(
    private val commandCalendarPort: CommandCalendarPort,
    private val fetchExternalCalendarPort: FetchExternalCalendarPort
) : SaveCalendarUseCase {
    @Retryable(
        value = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 2000)
    )
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

    @Recover
    fun recover(e: Exception, year: Int, month: Int) {
        log.info { "Failed to save calendar data for $year-$month: ${e.message}" }
    }
}
