package modeep.hear.global.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import modeep.hear.global.common.response.ErrorResponse
import modeep.hear.global.error.exception.GlobalErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets

@Component
class ErrorHandlingFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            // 필터에서 터진 에러를 여기서 잡아서 응답을 직접 만들어줍니다.
            handleException(request, response, e)
        }
    }

    private fun handleException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        e: Exception
    ) {
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()

        val errorResponse = ErrorResponse(
            code = GlobalErrorCode.INTERNAL_SERVER_ERROR.code,
            message = "필터 레벨에서 오류가 발생했습니다. Details: ${e.message}",
            path = request.requestURI
        )

        val errorBody = objectMapper.writeValueAsString(errorResponse)
        response.writer.write(errorBody)
    }
}
