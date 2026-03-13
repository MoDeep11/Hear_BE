package modeep.hear.global.error.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class GlobalErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String,
) : ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GLOBAL_001", "서버 내부에 오류가 발생했습니다"),
    INTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GLOBAL_002", "내부 서버 통신 중 오류가 발생했습니다"),
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "GLOBAL_003", "API 통신 중 오류가 발생했습니다"),
}