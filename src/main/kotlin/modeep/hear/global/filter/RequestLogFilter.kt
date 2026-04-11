package modeep.hear.global.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RequestLogFilter : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val wrappingRequest = ContentCachingRequestWrapper(request)

        val startTime = System.currentTimeMillis()
        filterChain.doFilter(wrappingRequest, response)
        val duration = System.currentTimeMillis() - startTime

        logRequestDetails(wrappingRequest, response.status, duration)
    }

    private fun logRequestDetails(request: ContentCachingRequestWrapper, status: Int, duration: Long) {
        val uri = request.requestURI
        val method = request.method
        val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
        val queryString = request.queryString?.let { "?$it" } ?: ""
        val clientIp = request.remoteAddr
        val payload = String(request.contentAsByteArray, StandardCharsets.UTF_8)

        log.info(
            """
            |
            |[TIMESTAMP] $now
            |[REQUEST] $method $uri$queryString | Status: $status | Time: ${duration}ms | IP: $clientIp
            |[PAYLOAD] ${payload.ifBlank { "No Body" }}
            |-------------------------------------------------------------------------
            """.trimMargin()
        )
    }
}
