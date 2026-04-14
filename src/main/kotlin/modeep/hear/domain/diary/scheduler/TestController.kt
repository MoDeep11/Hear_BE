package modeep.hear.domain.diary.scheduler

import modeep.hear.domain.user.scheduler.MonthlyReportScheduler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
    private val scheduler: DiaryScheduler,
    private val schedulerMe: MonthlyReportScheduler
) {
    @PostMapping("/a")
    fun test() {
        scheduler.scheduleRequestAiComment()
    }

    @PostMapping("/b")
    fun testB() {
        schedulerMe.createMonthlyStatistic()
    }
}
