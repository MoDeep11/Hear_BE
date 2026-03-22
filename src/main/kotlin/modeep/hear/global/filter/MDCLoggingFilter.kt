package modeep.hear.global.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class MDCLoggingFilter : OncePerRequestFilter() {

    companion object {
        private const val TRACE_ID = "traceId"
        private const val USER_ID = "userId"
        private const val ANONYMOUS = "anonymous"
        private const val HTTP_METHOD = "httpMethod"
        private const val REQUEST_URL = "requestUrl"
        private const val HEADER = "X-Trace-Id"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val auth = SecurityContextHolder.getContext().authentication
        val traceId = UUID.randomUUID().toString().take(8)

        val currentUserId = if (auth == null || auth.name == "anonymousUser") {
            ANONYMOUS
        } else {
            auth.name
        }

        MDC.put(TRACE_ID, traceId)
        MDC.put(USER_ID, currentUserId)
        MDC.put(HTTP_METHOD, request.method)
        MDC.put(REQUEST_URL, request.requestURI)

        response.setHeader(HEADER, traceId)

        try {
            filterChain.doFilter(request, response)
        } finally {
            // MDC 비우기 (ThreadLocal 오염 방지)
            MDC.clear()
        }
    }
}
