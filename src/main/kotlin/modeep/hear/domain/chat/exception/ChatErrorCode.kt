package modeep.hear.domain.chat.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class ChatErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode {
    CANNOT_ACCESS_CHAT(HttpStatus.FORBIDDEN, "CHAT_001", "해당 채팅 세션에 접근할 권한이 없습니다."),

    INVALID_GENERATION_REQUEST(HttpStatus.BAD_REQUEST, "CHAT_002", "이미지 생성 요청이 거절되었습니다.")
}
