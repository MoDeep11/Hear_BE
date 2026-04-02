package modeep.hear.global.error

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import modeep.hear.global.common.response.ErrorResponse
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.CriticalException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.global.util.maskIfSensitive
import modeep.hear.infrastructure.external.openfeign.discord.DiscordSendService
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.util.Locale

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler(
    private val discordSendService: DiscordSendService,
    private val messageSource: MessageSource
) {

    private fun resolveFieldName(field: String): String =
        messageSource.getMessage(field, null, field, Locale.KOREA) ?: field

    @ExceptionHandler(BusinessException::class)
    fun handlerBusinessException(e: BusinessException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode: ErrorCode = e.errorCode
        log.error { "[BUSINESS ERROR] ${errorCode.code}: ${errorCode.message}, Details: [${e.details}]" }

        return ResponseEntity
            .status(errorCode.status.value())
            .body(
                ErrorResponse(
                    code = errorCode.code,
                    message = errorCode.message,
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(CriticalException::class)
    fun handlerCriticalException(e: CriticalException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode: ErrorCode = e.errorCode
        log.error { "[CRITICAL] ${errorCode.code}: ${e.message}" }

        discordSendService.sendErrorLog(e, errorCode, request.requestURI)

        return ResponseEntity
            .status(errorCode.status.value())
            .body(
                ErrorResponse(
                    code = errorCode.code,
                    message = errorCode.message,
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error { "Validation failed for argument: ${e.bindingResult.fieldError?.field}" }
        val fieldErrors = e.bindingResult.fieldErrors
        val firstMessage = fieldErrors.firstOrNull()?.let { fieldError ->
            val fieldName = resolveFieldName(fieldError.field)
            fieldError.defaultMessage?.replace("{0}", fieldName)
        } ?: GlobalErrorCode.INVALID_INPUT_VALUE.message
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(
                ErrorResponse(
                    code = GlobalErrorCode.INVALID_INPUT_VALUE.code,
                    message = firstMessage,
                    path = request.requestURI,
                    errors = fieldErrors.map { fieldError ->
                        val fieldName = resolveFieldName(fieldError.field)
                        ErrorResponse.FieldError(
                            field = fieldError.field,
                            value = fieldError.rejectedValue?.toString().maskIfSensitive(fieldError.field),
                            reason = fieldError.defaultMessage?.replace("{0}", fieldName) ?: "unknown"
                        )
                    }
                )
            )
    }

    @ExceptionHandler(MissingRequestHeaderException::class)
    fun handleMissingRequestHeaderException(e: MissingRequestHeaderException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.warn { "MissingRequestHeaderException: ${e.headerName} header is missing" }
        return ResponseEntity
            .status(GlobalErrorCode.MISSING_REQUEST_HEADER.status.value())
            .body(
                ErrorResponse(
                    code = GlobalErrorCode.MISSING_REQUEST_HEADER.code,
                    message = "'${e.headerName}' ${GlobalErrorCode.MISSING_REQUEST_HEADER.message}",
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.warn { "HttpMessageNotReadableException: ${e.message}" }
        return ResponseEntity
            .status(GlobalErrorCode.INVALID_REQUEST_BODY.status.value())
            .body(
                ErrorResponse(
                    code = GlobalErrorCode.INVALID_REQUEST_BODY.code,
                    message = GlobalErrorCode.INVALID_REQUEST_BODY.message,
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.warn { "HttpRequestMethodNotSupportedException: ${e.method} is not supported" }
        return ResponseEntity
            .status(GlobalErrorCode.METHOD_NOT_ALLOWED.status.value())
            .body(
                ErrorResponse(
                    code = GlobalErrorCode.METHOD_NOT_ALLOWED.code,
                    message = GlobalErrorCode.METHOD_NOT_ALLOWED.message,
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerIllegalArgumentException(e: IllegalArgumentException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error { "IllegalArgumentException: ${e.message}, Cause: ${e.cause}" }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(
                ErrorResponse(
                    code = GlobalErrorCode.INVALID_REQUEST_BODY.code,
                    message = GlobalErrorCode.INVALID_REQUEST_BODY.message,
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(e: NoResourceFoundException, request: HttpServletRequest): ResponseEntity<Any> {
        log.warn { "Resource not found: ${e.resourcePath}" }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    code = GlobalErrorCode.RESOURCE_NOT_FOUND.code,
                    message = GlobalErrorCode.RESOURCE_NOT_FOUND.message,
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error(e) { "Unexpected Error: ${e.message}, Cause: ${e.cause}" }

        discordSendService.sendErrorLog(e, GlobalErrorCode.INTERNAL_SERVER_ERROR, request.requestURI)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(
                ErrorResponse(
                    code = GlobalErrorCode.INTERNAL_SERVER_ERROR.code,
                    message = GlobalErrorCode.INTERNAL_SERVER_ERROR.message,
                    path = request.requestURI
                )
            )
    }
}
