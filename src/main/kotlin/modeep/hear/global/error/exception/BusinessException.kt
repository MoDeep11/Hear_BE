package modeep.hear.global.error.exception

import modeep.hear.global.error.ErrorCode

class BusinessException(
    errorCode: ErrorCode,
    message: String? = null,
    details: String? = null
) : BaseException(errorCode, message, details)