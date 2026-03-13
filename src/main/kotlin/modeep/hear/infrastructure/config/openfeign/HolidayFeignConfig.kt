package modeep.hear.infrastructure.config.openfeign

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import feign.RequestInterceptor
import feign.Retryer
import feign.codec.Decoder
import modeep.hear.infrastructure.external.openfeign.holiday.HolidayFeignInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

class HolidayFeignConfig {
    @Value("\${api.holiday.service-key}")
    lateinit var serviceKey: String

    @Bean
    fun holidayInterceptor(): RequestInterceptor = HolidayFeignInterceptor(serviceKey)

    @Bean
    fun retryer(): Retryer {
        return Retryer.Default(100, 1000, 3) // period, maxPeriod, maxAttempts
    }

    @Bean
    fun feignDecoder(): Decoder {
        val objectMapper = ObjectMapper()
            .registerModule(KotlinModule.Builder().build())
            .registerModule(JavaTimeModule())
            // 데이터가 1개인 달
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            // "items": "" 같은 빈 문자열 처리 (데이터 없는 달)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            // DTO에 없는 필드
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        return ResponseEntityDecoder(SpringDecoder {
            HttpMessageConverters(MappingJackson2HttpMessageConverter(objectMapper))
        })
    }
}
