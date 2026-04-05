package modeep.hear.global.error

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import modeep.hear.global.common.response.ErrorResponse
import modeep.hear.global.error.exception.GlobalErrorCode
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class HttpAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val errorCode = GlobalErrorCode.FORBIDDEN
        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        objectMapper.writeValue(
            response.writer,
            ErrorResponse(
                code = errorCode.code,
                message = errorCode.message,
                path = request.requestURI
            )
        )
    }
}
