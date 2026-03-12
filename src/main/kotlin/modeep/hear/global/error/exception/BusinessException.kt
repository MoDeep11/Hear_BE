package modeep.hear.global.error.exception

import modeep.hear.global.error.ErrorCode

class BusinessException(
    errorCode: ErrorCode,
    details: String? = null,
) : BaseException(errorCode = errorCode, details = details)