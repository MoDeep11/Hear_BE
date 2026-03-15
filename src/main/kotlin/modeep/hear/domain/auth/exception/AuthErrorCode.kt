package modeep.hear.domain.auth.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode {

    PASSWORD_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "AUTH_001", "이미 사용되었거나 만료된 토큰입니다."),
    EMAIL_ALREADY_VERIFIED(HttpStatus.CONFLICT, "AUTH_002", "이미 인증이 완료된 요청입니다."),
    VERIFICATION_TIMEOUT(HttpStatus.GONE, "AUTH_003", "인증 시간이 만료되었습니다."),

    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH_004", "해당 이메일의 사용자를 찾을 수 없습니다.")
}
