package modeep.hear.domain.user.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import modeep.hear.domain.user.model.MonthlyStatistic
import modeep.hear.domain.user.port.`in`.CreateMonthlyReportUseCase
import modeep.hear.domain.user.port.out.MonthlyStatisticPort
import modeep.hear.domain.user.port.out.query.QueryUserPort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.YearMonth

@Component
class MonthlyReportScheduler(
    private val queryUserPort: QueryUserPort,
    private val monthlyStatisticPort: MonthlyStatisticPort,
    private val createMonthlyReportUseCase: CreateMonthlyReportUseCase
) {
    private val log = KotlinLogging.logger {}
    val scheduleScope = runBlocking { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    @Scheduled(cron = "0 40 11 L * *", zone = "Asia/Seoul")
    fun createMonthlyStatistic() {
        val nextMonth = YearMonth.now().plusMonths(1)
        log.info { "Monthly Report scheduler started: target=[$nextMonth]" }

        val userIds = queryUserPort.findAllIds()
        if (userIds.isEmpty()) return

        userIds.forEach { userId ->
            monthlyStatisticPort.save(
                MonthlyStatistic.create(
                    userId = userId,
                    targetYearMonth = nextMonth
                )
            )
        }

        scheduleScope.launch {
            userIds.chunked(100).forEach { batch ->
                batch.map { id ->
                    launch {
                        runCatching {
                            createMonthlyReportUseCase.execute(id)
                        }.onFailure { e ->
                            log.error(e) { "Error for user: [$id]" } // 실패해도 계속 진행
                        }
                    }
                }.joinAll()

                delay(1000)
            }
        }
    }

    @PostConstruct
    fun init() {
        createMonthlyStatistic()
    }
}
