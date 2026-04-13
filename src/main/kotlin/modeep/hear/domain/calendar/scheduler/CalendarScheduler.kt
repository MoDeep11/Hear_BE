package modeep.hear.domain.calendar.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.calendar.exception.CalendarErrorCode
import modeep.hear.domain.calendar.port.`in`.SyncCalendarUseCase
import modeep.hear.global.error.ExceptionNotifier
import modeep.hear.global.error.exception.BusinessException
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CalendarScheduler(
    private val syncCalendarUseCase: SyncCalendarUseCase,
    private val exceptionNotifier: ExceptionNotifier
) {
    private val log = KotlinLogging.logger {}

    @Async("calendarAsyncExecutor")
    @EventListener(ApplicationReadyEvent::class)
    fun initCalendarData() {
        val targetYear = LocalDate.now().year
        log.info { "Server init: Start to create calendar data about $targetYear year." }

        val result = saveYearlyCalendar(targetYear)

        if (result.isSuccess) {
            log.info { "Complete $targetYear year calendar data creation." }
        } else {
            log.error { "Failed to create calendar data for $targetYear" }

            result.lastError?.let {
                exceptionNotifier.notify(
                    it,
                    CalendarErrorCode.CALENDAR_SYNC_PARTIAL_FAILED,
                    "Scheduler: Calendar"
                )
            }
        }
    }

    @Scheduled(cron = "0 0 2 1 12 ?", zone = "Asia/Seoul")
    fun scheduleNextYearCalendar() {
        val nextYear = LocalDate.now().year + 1
        log.info { "Regular Schedule: Generates calendar data for the $nextYear year in advance." }
        saveYearlyCalendar(nextYear)
    }

    private fun saveYearlyCalendar(year: Int): SyncResult {
        var lastError: Exception? = null

        try {
            syncCalendarUseCase.execute(year)
            Thread.sleep(100) // API 과부하 방지
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            log.warn { "스케줄러 작업이 종료되었습니다: ${e.message}" }
        } catch (e: BusinessException) {
            log.error { "Error [year=$year]: ${e.errorCode.code} - ${e.message}" }
            lastError = e
        } catch (e: Exception) {
            lastError = e
        }
        return SyncResult(year, lastError)
    }

    data class SyncResult(
        val year: Int,
        val lastError: Exception? = null
    ) {
        val isSuccess: Boolean = lastError == null
    }
}
