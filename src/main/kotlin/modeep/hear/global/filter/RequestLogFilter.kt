package modeep.hear.global.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import modeep.hear.global.common.constant.LogColor
import modeep.hear.global.common.constant.SecurityConstants
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RequestLogFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val wrappingRequest = ContentCachingRequestWrapper(request)
        val wrappingResponse = ContentCachingResponseWrapper(response)

        val startTime = System.currentTimeMillis()

        try {
            filterChain.doFilter(wrappingRequest, wrappingResponse)
        } finally {
            val duration = System.currentTimeMillis() - startTime
            logRequestDetails(wrappingRequest, wrappingResponse, duration)
            wrappingResponse.copyBodyToResponse()
        }
    }

    private fun logRequestDetails(
        request: ContentCachingRequestWrapper,
        response: ContentCachingResponseWrapper,
        duration: Long
    ) {
        val uri = request.requestURI
        val method = request.method
        val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
        val queryString = request.queryString?.let { "?$it" } ?: ""
        val clientIp = request.remoteAddr

        val responseBody = String(response.contentAsByteArray, StandardCharsets.UTF_8)
        val errorCode = extractErrorCode(responseBody)

        val status = response.status
        val statusColor = when {
            status >= 500 -> LogColor.RED
            status >= 400 -> LogColor.ORANGE
            else -> LogColor.GREEN
        }

        val rawPayload = String(request.contentAsByteArray, StandardCharsets.UTF_8)
        val maskedPayload = getMaskedPayload(rawPayload)

        val codeColor = if (errorCode != null) LogColor.RED else LogColor.GREEN

        log.info(
            """
            |
            |${LogColor.CYAN}[TIMESTAMP]${LogColor.RESET} $now 
            |${LogColor.BLUE}[REQUEST]${LogColor.RESET}   $method $uri$queryString | ${statusColor}Status: $status${LogColor.RESET} | Time: ${duration}ms
            |${LogColor.BLUE}[CODE]${LogColor.RESET}      ${codeColor}${errorCode ?: "SUCCESS (N/A)"}${LogColor.RESET}
            |${LogColor.CYAN}[IP]${LogColor.RESET}        $clientIp
            |${LogColor.CYAN}[PAYLOAD]${LogColor.RESET}   ${maskedPayload.ifBlank { "No Body" }}
            |-------------------------------------------------------------------------
            """.trimMargin()
        )
    }

    private fun extractErrorCode(json: String): String? {
        if (json.isBlank()) return null

        return try {
            val rootNode = objectMapper.readTree(json)
            rootNode.path("code").asText(null)
        } catch (e: Exception) {
            null
        }
    }

    private fun getMaskedPayload(payload: String): String {
        if (payload.isBlank()) return "No Body"
        return try {
            val rootNode = objectMapper.readTree(payload)
            if (rootNode.isObject) {
                val objectNode = rootNode as ObjectNode
                SecurityConstants.SENSITIVE_FIELDS.forEach { field ->
                    if (objectNode.has(field)) {
                        objectNode.put(field, SecurityConstants.MASKING_TEXT)
                    }
                }
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode)
            } else {
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode)
            }
        } catch (e: Exception) {
            payload
        }
    }
}
