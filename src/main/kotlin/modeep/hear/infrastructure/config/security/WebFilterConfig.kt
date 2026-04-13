package modeep.hear.infrastructure.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import modeep.hear.global.filter.RequestLogFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class WebFilterConfig(
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun requestLogFilter(): FilterRegistrationBean<RequestLogFilter> {
        val registration = FilterRegistrationBean(RequestLogFilter(objectMapper))
        registration.order = Ordered.HIGHEST_PRECEDENCE
        return registration
    }
}
