package modeep.hear.domain.diary.scheduler

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
    private val scheduler: DiaryScheduler
) {
    @PostMapping
    fun test() {
        scheduler.scheduleRequestAiComment()
    }
}
