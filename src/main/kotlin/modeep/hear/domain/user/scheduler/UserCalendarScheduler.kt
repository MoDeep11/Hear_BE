package modeep.hear.domain.user.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.user.port.out.UserCalendarPort
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.YearMonth

private val log = KotlinLogging.logger {}

@Component
class UserCalendarScheduler(
    private val userCalendarPort: UserCalendarPort,
    private val userRepository: UserRepository
) {
    @Scheduled(cron = "0 5 0 1 * *", zone = "Asia/Seoul")
    fun createNextMonthUserCalendars() {
        val nextMonth = YearMonth.now().plusMonths(1)
        log.info { "UserCalendar scheduler started: target=$nextMonth" }

        val userIds = userRepository.findAllIds()
        if (userIds.isEmpty()) {
            log.info { "No users found. Skipping UserCalendar creation." }
            return
        }

        runCatching {
            userCalendarPort.saveAllForAllUsers(userIds, nextMonth)
        }.onSuccess {
            log.info { "UserCalendar creation completed: target=$nextMonth, users=${userIds.size}" }
        }.onFailure { e ->
            log.error(e) { "UserCalendar creation failed: target=$nextMonth" }
        }
    }
}
