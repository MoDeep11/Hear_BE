package modeep.hear.domain.calendar.service.component

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.out.CommandCalendarPort
import modeep.hear.domain.calendar.port.out.QueryCalendarPort
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

private val log = KotlinLogging.logger {}

@Service
class SaveCalendarComponent(
    private val commandCalendarPort: CommandCalendarPort,
    private val queryCalendarPort: QueryCalendarPort
) {

    @Retryable(
        value = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 2000)
    )
    @Transactional
    fun execute(year: Int, month: Int, holidays: Set<LocalDate>): List<Calendar> {
        val start = LocalDate.of(year, month, 1)
        val end = start.withDayOfMonth(start.lengthOfMonth())

        commandCalendarPort.deleteByCalendarDateBetween(start, end)

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

    // 디버깅을 위해서는 Recover를 사용하면 안된다.
    // 데이터 무결성이 중요한 경우(예: A 데이터를 못 찾았을 때 B 데이터라도 보여줘야 하는 경우)에 사용할 수 있다
//    @Recover
//    fun recover(e: Exception, year: Int, month: Int): List<Calendar> {
//        log.error(e) { "Failed to save calendar data for $year-$month: ${e.message}" }
//        return emptyList()
//    }
}
