package modeep.hear.global.error.exception

import modeep.hear.global.error.ErrorCode

open class BaseException(
    val errorCode: ErrorCode,
    override val message: String? = null,
    val details: String? = null,
) : RuntimeException()