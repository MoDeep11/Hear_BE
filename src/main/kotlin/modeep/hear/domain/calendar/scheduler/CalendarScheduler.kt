package modeep.hear.domain.calendar.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.calendar.port.`in`.SaveCalendarUseCase
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

private val log = KotlinLogging.logger {}

@Component
class CalendarScheduler(
    private val saveCalendarUseCase: SaveCalendarUseCase,
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
        val failedMonths = mutableListOf<Int>()

        (1..12).forEach { month ->
            try {
                saveCalendarUseCase.execute(year, month)
                Thread.sleep(100)  // API 과부하 방지
            } catch (e: Exception) {
                log.error { "$month 월 실패: ${e.message}" }
                failedMonths.add(month) // 실패한 월 저장
            }
        }

        if (failedMonths.isNotEmpty()) {
            log.warn { "⚠️ 작업 완료. 실패한 월: $failedMonths (총 ${failedMonths.size}건)" }
        } else {
            log.info { "🎉 모든 월이 성공적으로 동기화되었습니다." }
        }
    }
}