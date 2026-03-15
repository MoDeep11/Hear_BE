package modeep.hear.domain.calendar.service.component

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.out.CommandCalendarPort
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.Year

private val log = KotlinLogging.logger {}

@Service
class SaveCalendarComponent(
    private val commandCalendarPort: CommandCalendarPort
) {
    @Retryable(
        value = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 2000)
    )
    @Transactional
    fun execute(year: Int, holidays: Set<LocalDate>): List<Calendar> {
        val (start, end) = getYearRange(year)
        commandCalendarPort.deleteByCalendarDateBetween(start, end)

        val calendars = (0 until end.lengthOfYear()).map { daysToAdd ->
            val currentDate = start.plusDays(daysToAdd.toLong())
            Calendar.create(
                date = currentDate,
                isHoliday = holidays.contains(currentDate)
            )
        }
        return commandCalendarPort.saveAll(calendars)
    }

    private fun getYearRange(yearInt: Int): Pair<LocalDate, LocalDate> {
        val year = Year.of(yearInt)
        val start = year.atDay(1)
        val end = year.atDay(year.length())
        return Pair(start, end)
    }

    // 디버깅을 위해서는 Recover를 사용하면 안된다.
    // 데이터 무결성이 중요한 경우(예: A 데이터를 못 찾았을 때 B 데이터라도 보여줘야 하는 경우)에 사용할 수 있다
//    @Recover
//    fun recover(e: Exception, year: Int, month: Int): List<Calendar> {
//        log.error(e) { "Failed to save calendar data for $year-$month: ${e.message}" }
//        return emptyList()
//    }
}
