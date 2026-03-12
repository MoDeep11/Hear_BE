package modeep.hear.domain.calendar.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.calendar.exception.CalendarErrorCode
import modeep.hear.domain.calendar.port.`in`.SaveCalendarUseCase
import modeep.hear.global.error.exception.BusinessException
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

        log.info { "Complete $targetYear year calendar data creation." }
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
            } catch (e: BusinessException) {
                log.error { "Error [$year-$month]: ${e.errorCode.code} - ${e.message}" }
                failedMonths.add(month)
            } catch (e: Exception) {
                log.error(e) { "System Error: ${e.message}" }
                failedMonths.add(month)
            }
        }

        if (failedMonths.isNotEmpty()) {
            log.warn { "$year 년 작업 완료 (실패한 월: $failedMonths). 확인이 필요합니다." }
            throw BusinessException(
                errorCode = CalendarErrorCode.CALENDAR_SYNC_PARTIAL_FAILED,
                message = "$year 년도 중 다음 달의 동기화에 실패했습니다: $failedMonths"
            )
        } else {
            log.info { "$year 년 모든 달력이 성공적으로 동기화되었습니다." }
        }
    }
}