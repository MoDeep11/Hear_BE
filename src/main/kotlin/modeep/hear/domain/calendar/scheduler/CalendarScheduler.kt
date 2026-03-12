package modeep.hear.domain.calendar.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.calendar.service.SaveCalendarService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

private val log = KotlinLogging.logger {}

@Component
class CalendarScheduler(
    private val saveCalendarService: SaveCalendarService
) {

    @EventListener(ApplicationReadyEvent::class)
    fun initCalendarData() {
        val targetYear = LocalDate.now().year
        log.info { "Server init: Start to create calendar data about $targetYear year." }

        saveYearlyCalendar(targetYear)

        println("Complete $targetYear year calendar data creation.")
    }

    @Scheduled(cron = "0 0 2 1 12 ?", zone = "Asia/Seoul")
    fun scheduleNextYearCalendar() {
        val nextYear = LocalDate.now().year + 1
        log.info { "Regular Schedule: Generates calendar data for the $nextYear year in advance." }
        saveYearlyCalendar(nextYear)
    }

    private fun saveYearlyCalendar(year: Int) {
        (1..12).forEach { month ->
            try {
                saveCalendarService.execute(year, month)
                Thread.sleep(100)  // API 과부하 방지
            } catch (e: Exception) {
                TODO("Error handling")
                // println("Error: $year 년 $month 월 데이터 생성 중 오류 발생: ${e.message}")
            }
        }
    }
}