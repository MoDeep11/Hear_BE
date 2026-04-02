package modeep.hear.global.error.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class GlobalErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GLOBAL_001", "서버 내부에 오류가 발생했습니다"),
    AI_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "GLOBAL_002", "내부 서버 통신 중 오류가 발생했습니다"),
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "GLOBAL_003", "API 통신 중 오류가 발생했습니다"),

    JSON_CONVERSION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GLOBAL_004", "데이터 변환 중 오류가 발생했습니다."),

    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "GLOBAL_005", "요청 본문을 읽을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "GLOBAL_006", "지원하지 않는 HTTP 메서드입니다."),

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "GLOBAL_007", "입력값이 유효하지 않습니다."),
    ILLEGAL_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, "GLOBAL_008", "잘못된 인자가 전달되었습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "GLOBAL_009", "리소스를 찾을 수 없습니다.")
}
