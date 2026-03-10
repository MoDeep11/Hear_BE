package modeep.hear.infrastructure.config.openfeign

import feign.Logger
import feign.Request
import feign.Retryer
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableFeignClients(basePackages = ["modeep.hear"])
class OpenFeignConfig {
    @Bean
    fun feignLoggerLevel(): Logger.Level = Logger.Level.FULL

    @Bean
    fun options(): Request.Options =
        Request.Options(
            5000,
            TimeUnit.MILLISECONDS, // connectTimeout
            5000,
            TimeUnit.MILLISECONDS, // readTimeout
            true, // followRedirects
        )
}
