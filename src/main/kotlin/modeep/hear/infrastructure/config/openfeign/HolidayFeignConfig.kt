package modeep.hear.infrastructure.config.openfeign

import feign.RequestInterceptor
import feign.Retryer
import modeep.hear.infrastructure.external.openfeign.holiday.HolidayFeignInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class HolidayFeignConfig {
    @Value("\${api.holiday.service-key}")
    lateinit var serviceKey: String

    @Bean
    fun holidayInterceptor(): RequestInterceptor = HolidayFeignInterceptor(serviceKey)

    @Bean
    fun retryer(): Retryer {
        return Retryer.Default(100, 1000, 3) // period, maxPeriod, maxAttempts
    }
}
