package modeep.hear.domain.common.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String,
) : ErrorCode {
    INVALID_EMOTION_EXCEPTION(HttpStatus.)
}