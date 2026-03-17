package modeep.hear.domain.user.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class UserErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode {

    INVALID_VALUE(HttpStatus.BAD_REQUEST, "USER_001", "유효하지 않은 값입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_003", "해당 이메일의 사용자를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_004", "해당 사용자를 찾을 수 없습니다."),
}
