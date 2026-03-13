package modeep.hear.infrastructure.config.env

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .disable(WRITE_DATES_AS_TIMESTAMPS) // ISO-8601 표준 문자열
            .setSerializationInclusion(JsonInclude.Include.NON_NULL) // null 제외
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE) // DB 컬럼 네이밍: 스네이크 케이스
    }
}
