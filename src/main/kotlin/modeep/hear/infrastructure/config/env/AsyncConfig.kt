package modeep.hear.infrastructure.config.env

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.global.filter.MdcTaskDecorator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

private val log = KotlinLogging.logger {}

@Configuration
@EnableAsync
class AsyncConfig : AsyncConfigurer {

    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.setTaskDecorator(MdcTaskDecorator()) // 여기서 등록!
        executor.initialize()
        return executor
    }

    @Bean(name = ["discordAsyncExecutor"])
    fun discordAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 10
        executor.queueCapacity = 100
        executor.setThreadNamePrefix("DiscordAsync-")

        executor.setRejectedExecutionHandler { runnable, exec ->
            log.warn { "에러 알림 큐 포화: 작업 드랍됨 (Active: ${exec.activeCount})" }
        }

        executor.initialize()
        return executor
    }

    @Bean(name = ["calendarAsyncExecutor"])
    fun calendarAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 4
        executor.maxPoolSize = 20
        executor.queueCapacity = 50
        executor.setThreadNamePrefix("CalendarAsync-")

        // 앱 종료 시 진행 중인 작업은 마저 끝내고 닫기
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.setAwaitTerminationSeconds(60)

        // 큐가 꽉 찼을 때 호출한 스레드(메인 스레드 등)가 직접 그 작업을 처리하게 함
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())

        executor.initialize()
        return executor
    }

    @Bean(name = ["mailAsyncExecutor"])
    fun mailAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 15
        executor.queueCapacity = 200
        executor.setThreadNamePrefix("MailAsync-")
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())

        executor.initialize()
        return executor
    }

    @Bean(name = ["storageAsyncExecutor"])
    fun storageAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 15
        executor.queueCapacity = 200
        executor.setThreadNamePrefix("StorageAsync-")
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())

        executor.initialize()
        return executor
    }
}
