package modeep.hear.global.error

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import modeep.hear.global.common.response.ErrorResponse
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.CriticalException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.external.openfeign.discord.DiscordSendService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler(
    private val discordSendService: DiscordSendService,
) {

    @ExceptionHandler(BusinessException::class)
    fun handlerBusinessException(e: BusinessException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode : ErrorCode = e.errorCode
        log.error { "[BUSINESS ERROR] ${errorCode.code}: ${errorCode.message}], Details: [${e.details}]" }

        return ResponseEntity
            .status(errorCode.status.value())
            .body(ErrorResponse(
                code = errorCode.code,
                message = errorCode.message,
                path = request.requestURI
            ))
    }

    @ExceptionHandler(CriticalException::class)
    fun handlerCriticalException(e: CriticalException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorCode : ErrorCode = e.errorCode
        log.error { "[CRITICAL] ${errorCode.code}: ${e.message}" }

        discordSendService.sendErrorLog(e, errorCode, request.requestURI)

        return ResponseEntity
            .status(errorCode.status.value())
            .body(ErrorResponse(
                code = errorCode.code,
                message = errorCode.message,
                path = request.requestURI,
            ))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error { "Validation failed for argument: ${e.bindingResult.fieldError?.field}" }
        val fieldError = e.bindingResult.fieldErrors.firstOrNull()
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(ErrorResponse(
                code = "INVALID_INPUT_VALUE", // todo: error code
                message = fieldError?.defaultMessage ?: "입력값이 유효하지 않습니다.",
                path = request.requestURI,
                errors = listOf(
                    ErrorResponse.FieldError(
                        field = fieldError?.field ?: "unknown",
                        value = fieldError?.rejectedValue?.toString() ?: "unknown",
                        reason = fieldError?.defaultMessage ?: "unknown"
                    )
                ),
            ))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerIllegalArgumentException(e: IllegalArgumentException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error { "IllegalArgumentException: ${e.message}, Cause: ${e.cause}" }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(ErrorResponse(
                code = "ILLEGAL_ARGUMENT_ERROR",
                message = "입력값이 유효하지 않습니다.",
                path = request.requestURI,
            ))
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error(e) { "Unexpected Error: ${e.message}, Cause: ${e.cause}" }

        discordSendService.sendErrorLog(e, GlobalErrorCode.INTERNAL_SERVER_ERROR, request.requestURI)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(ErrorResponse(
                code = GlobalErrorCode.INTERNAL_SERVER_ERROR.code,
                message = GlobalErrorCode.INTERNAL_SERVER_ERROR.message,
                path = request.requestURI,
            ))
    }
}