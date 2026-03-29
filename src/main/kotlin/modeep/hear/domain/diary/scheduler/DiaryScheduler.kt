package modeep.hear.domain.diary.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import modeep.hear.domain.diary.port.`in`.CreateDiaryAiCommentUseCase
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime

private val log = KotlinLogging.logger {}

@Component
class DiaryScheduler(
    private val createDiaryAiCommentUseCase: CreateDiaryAiCommentUseCase,
    private val queryDiaryPort: QueryDiaryPort
) {
    private val scheduleScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
    fun scheduleRequestAiComment() {
        val yesterday = LocalDate.now().minusDays(1)
        val start = yesterday.atStartOfDay()
        val end = yesterday.atTime(LocalTime.MAX)

        val targetDiaries = queryDiaryPort.findAllByCreatedAtBetween(start, end)

        scheduleScope.launch {
            targetDiaries.chunked(100).forEach { batch ->
                batch.forEach { diary ->
                    launch { // 각 일기를 개별 코루틴으로 실행 (병렬)
                        runCatching {
                            createDiaryAiCommentUseCase.execute(diary)
                        }.onSuccess {
                            log.info { "Successfully added AI Comment: [${diary.id}]" }
                        }.onFailure { e ->
                            log.error(e) { "Failed to add AI Comment for diary: [${diary.id}]" }
                        }
                    }
                }
                // 1초 대기 (서버 과부하 방지)
                delay(1000)
                log.info { "Batch processed. Sleeping for 1s..." }
            }
        }
    }
}

// runBlocking: 스레드를 점유(blocking)하고 내부 코루틴이 완료될 때까지 대기하는 방식
// 순차적으로 작업을 처리한다. 이 과정에서 작업이 완료될 때까지 스레드를 계속 점유한다.
// 블로킹 처리로 인해 한 스케줄러가 스레드를 계속 점유하면 시스템 전체의 응답성이 감소하고, 코루틴의 특성을 제대로 활용하지 못함

// CoroutineScope: 별도의 Scope를 만들어 launch로 던지는 방식으로, 논블로킹(Non-blocking) 방식
// 비차단 실행: launch를 호출하자마자 스케줄러 스레드가 즉시 해제되어 다음 작업을 처리할 수 있다
// 병렬 제어: async, launch를 통해 여러 작업을 동시에 실행하고 제어할 수 있다. (병렬 처리)
// launch vs async:
// * launch는 결과 값을 받지 않는 단순 상태 변경에 사용, 에러 발생 시 코루틴 전체에 전파(Fire and Forget)
// * async는 await() 메서드를 통해 결과 값을 받고 연산을 할 수 있음, 에러 발생 시 await() 단계에서 발견(Call and Wait)
// 생명주기 관리: 스케줄러 스레드가 이미 종료되었음에도 백그라운드 코루틴이 계속 살아있을 수 있다. 서버 종료 시 Graceful Shutdown(안전한 종료)를 고려해야 한다.

// 해당 스케줄러는 단순 상태 변경임으로 launch 사용
