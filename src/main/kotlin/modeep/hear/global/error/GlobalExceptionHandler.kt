package modeep.hear.global.error

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.global.common.response.ErrorResponse
import modeep.hear.global.error.exception.BaseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BaseException::class)
    fun handlerBaseException(e: BaseException): ResponseEntity<ErrorResponse> {
        val errorCode : ErrorCode = e.errorCode
        log.error { "Error Code: [${errorCode.code}], Error Message: [${errorCode.message}], Details: [${e.details}]" }

        return ResponseEntity
            .status(errorCode.status.value())
            .body(ErrorResponse(
                code = errorCode.code,
                message = errorCode.message,
            ))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        log.error { "Validation failed for argument: ${e.bindingResult.fieldError?.field}" }
        val fieldError = e.bindingResult.fieldErrors.firstOrNull()
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(ErrorResponse(
                code = "INVALID_INPUT_VALUE", // todo: error code
                message = fieldError?.defaultMessage ?: "입력값이 유효하지 않습니다.",
            ))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        log.error { "IllegalArgumentException: ${e.message}, Cause: ${e.cause}" }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(ErrorResponse(
                code = "ILLEGAL_ARGUMENT_ERROR",
                message = "유효하지 않은 값이 들어왔습니다.",
            ))
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e) { "Unexpected Error: ${e.message}, Cause: ${e.cause}" }
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(ErrorResponse(
                code = "INTERNAL_SERVER_ERROR",
                message = "서버 내부 오류가 발생했습니다.",
            ))
    }
}