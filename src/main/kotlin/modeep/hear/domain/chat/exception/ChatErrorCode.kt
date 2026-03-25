package modeep.hear.domain.chat.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class ChatErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode
