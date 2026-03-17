package modeep.hear.global.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class MDCLoggingFilter : OncePerRequestFilter() {

    companion object {
        private const val USER_ID = "userId"
        private const val HEADER = "X-Trace-Id"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val principal = SecurityContextHolder.getContext().authentication.principal

        MDC.put(USER_ID, uuid)
        MDC.put("user_id", principal?.userId)
        MDC.put("user_type", principal?.userType.toString())
        MDC.put("http_method", httpRequest.method)
        MDC.put("request_url", httpRequest.requestURI)
        response.setHeader(HEADER, uuid)

        try {
            filterChain.doFilter(request, response)
        } finally {
            // MDC 비우기 (ThreadLocal 오염 방지)
            MDC.clear()
        }
    }
    @Component
    class MDCLoggingFilter: Filter {
        override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
            val httpRequest = request as HttpServletRequest
            val principal = SecurityContextHolder.getContext().authentication.principal as? MustItPrincipal

            MDC.put("user_id", principal?.userId)
            MDC.put("user_type", principal?.userType.toString())
            MDC.put("http_method", httpRequest.method)
            MDC.put("request_url", httpRequest.requestURI)

            chain.doFilter(request, response)
            MDC.clear()
        }
    }
}