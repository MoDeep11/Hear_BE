package modeep.hear.infrastructure.config.env

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

private val log = KotlinLogging.logger {}

@Configuration
@EnableAsync
class AsyncConfig {
    @Bean(name = ["discordAsyncExecutor"])
    fun threadPoolTaskExecutor(): Executor {
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
}
