package modeep.hear.global.error.exception

import modeep.hear.global.error.ErrorCode

class CriticalException(
    errorCode: ErrorCode,
    message: String = errorCode.message,
    val payload: Map<String, Any>? = null
) : BaseException(errorCode, message)
