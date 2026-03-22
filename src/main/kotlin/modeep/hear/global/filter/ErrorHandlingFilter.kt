package modeep.hear.global.filter

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import modeep.hear.global.common.response.ErrorResponse
import modeep.hear.global.error.exception.GlobalErrorCode
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import java.nio.charset.StandardCharsets

private val log = KotlinLogging.logger {}

class ErrorHandlingFilter(
    private val objectMapper: ObjectMapper,
    @param:Qualifier("handlerExceptionResolver")
    private val exceptionResolver: HandlerExceptionResolver
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            if (!response.isCommitted) {
                exceptionResolver.resolveException(request, response, null, e)
            }
        }
    }

    @Deprecated("Not used")
    private fun handleException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        e: Exception
    ) {
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()

        log.error(e) { "Filter Error: ${e.message}, Cause: ${e.cause}" }

        val errorResponse = ErrorResponse(
            code = GlobalErrorCode.INTERNAL_SERVER_ERROR.code,
            message = "필터 레벨에서 오류가 발생했습니다",
            path = request.requestURI
        )

        val errorBody = objectMapper.writeValueAsString(errorResponse)
        response.writer.write(errorBody)
    }
}
