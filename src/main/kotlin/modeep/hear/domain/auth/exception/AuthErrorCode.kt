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

    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTH_004", "비밀번호가 일치하지 않습니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_005", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_006", "만료된 토큰입니다."),
    LOGOUT_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_007", "로그아웃된 토큰입니다")
}
