package modeep.hear.global.util

import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.global.error.exception.BusinessException

fun String.maskUri(): String {
    val sensitiveKeys = listOf("ServiceKey", "accessToken", "auth", "token")

    var maskedUri = this
    sensitiveKeys.forEach { key ->
        val regex = Regex("($key=)[^&]*")
        maskedUri = maskedUri.replace(regex, "$1********")
    }
    return maskedUri
}

fun String?.maskIfSensitive(fieldName: String): String {
    if (this == null) return ""

    val sensitiveFields = listOf("password", "account", "ssn", "token", "credential")

    val isSensitive = sensitiveFields.any { fieldName.contains(it, ignoreCase = true) }

    return if (isSensitive) "$1********" else this
}

fun String.checkBlank(label: String) {
    if (this.isBlank()) {
        throw BusinessException(
            UserErrorCode.INVALID_VALUE,
            "$label 은 비어있을 수 없습니다."
        )
    }
}
