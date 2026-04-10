package modeep.hear.infrastructure.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import modeep.hear.global.filter.ErrorHandlingFilter
import modeep.hear.global.filter.MDCLoggingFilter
import modeep.hear.global.filter.RequestLogFilter
import modeep.hear.infrastructure.security.jwt.JwtAdapter
import modeep.hear.infrastructure.security.jwt.JwtFilter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.context.SecurityContextHolderFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import kotlin.jvm.java

class FilterConfig(
    private val jwtAdapter: JwtAdapter,
    private val objectMapper: ObjectMapper,
    private val exceptionResolver: HandlerExceptionResolver
) : AbstractHttpConfigurer<FilterConfig, HttpSecurity>() {
    override fun configure(http: HttpSecurity) {
        val mdcLoggingFilter = MDCLoggingFilter()
        val requestLogFilter = RequestLogFilter()
        val jwtFilter = JwtFilter(jwtAdapter)
        val errorHandlingFilter = ErrorHandlingFilter(
            objectMapper = objectMapper,
            exceptionResolver = exceptionResolver
        )

        http.addFilterBefore(mdcLoggingFilter, SecurityContextHolderFilter::class.java)
            .addFilterAfter(requestLogFilter, MDCLoggingFilter::class.java)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(errorHandlingFilter, JwtFilter::class.java)
    }
}
