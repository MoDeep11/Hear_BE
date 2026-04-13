package modeep.hear.global.error

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import modeep.hear.global.common.response.ErrorResponse
import modeep.hear.global.error.exception.GlobalErrorCode
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class HttpAuthEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorCode = GlobalErrorCode.UNAUTHORIZED

        val json = objectMapper.writeValueAsString(
            ErrorResponse(
                code = errorCode.code,
                message = errorCode.message,
                path = request.requestURI
            )
        )

        response.reset()
        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.setContentLength(json.toByteArray(Charsets.UTF_8).size)
        response.writer.write(json)
        response.writer.flush()
    }
}
