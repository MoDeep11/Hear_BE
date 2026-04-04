package modeep.hear.domain.auth.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode {

    INVALID_TICKET(HttpStatus.BAD_REQUEST, "AUTH_001", "유효하지 않은 티켓입니다."),
    EMAIL_ALREADY_VERIFIED(HttpStatus.CONFLICT, "AUTH_002", "이미 인증이 완료된 요청입니다."),
    VERIFICATION_TIMEOUT(HttpStatus.UNAUTHORIZED, "AUTH_003", "인증 시간이 만료되었습니다."),
    TOO_MANY_EMAIL_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "AUTH_004", "인증번호 요청은 1분에 한 번만 가능합니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "AUTH_005", "인증 코드가 일치하지 않습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "AUTH_006", "해당 이메일로 이미 가입한 계정이 있습니다."),

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH_010", "존재하지 않는 리프레시 토큰입니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_011", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_012", "만료된 토큰입니다."),
    LOGOUT_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_013", "로그아웃된 토큰입니다"),

    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTH_021", "비밀번호가 일치하지 않습니다."),
    INVALID_LOGIN_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_022", "유저 정보가 일치하지 않습니다.")
}
