package modeep.hear.infrastructure.config.env

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import modeep.hear.global.common.constant.SecurityConstants
import modeep.hear.global.common.serializer.MaskingSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val javaTimeModule = JavaTimeModule().apply {
            addSerializer(
                LocalDateTime::class.java,
                LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
            addDeserializer(
                LocalDateTime::class.java,
                LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
        }

        return jacksonObjectMapper()
            .registerModule(javaTimeModule)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // 커스텀 포맷: yyyy-MM-dd HH:mm:ss
            .setSerializationInclusion(JsonInclude.Include.NON_NULL) // null 제외
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .withMasking()
    }

    private fun ObjectMapper.withMasking(): ObjectMapper {
        val modifier = object : BeanSerializerModifier() {
            override fun changeProperties(
                config: SerializationConfig,
                beanDesc: BeanDescription,
                beanProperties: MutableList<BeanPropertyWriter>
            ): MutableList<BeanPropertyWriter> {
                beanProperties.forEach { writer ->
                    if (isSensitiveField(writer.name)) {
                        writer.assignSerializer(MaskingSerializer())
                    }
                }
                return beanProperties
            }

            private fun isSensitiveField(name: String): Boolean {
                return SecurityConstants.SENSITIVE_FIELDS.any { it.equals(name, ignoreCase = true) }
            }
        }

        return this.setSerializerFactory(this.serializerFactory.withSerializerModifier(modifier))
    }
}
