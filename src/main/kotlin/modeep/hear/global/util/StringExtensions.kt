package modeep.hear.global.util

import modeep.hear.domain.storage.exception.StorageErrorCode
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.global.error.exception.BusinessException

fun String.maskUri(): String {
    val sensitiveKeys = listOf("ServiceKey", "accessToken", "auth", "token")

    val keyPattern = sensitiveKeys.joinToString("|") { Regex.escape(it) }
    val regex = Regex("""(^|[?&])($keyPattern)=([^&#]*)""", RegexOption.IGNORE_CASE)

    return regex.replace(this) { m ->
        "${m.groupValues[1]}${m.groupValues[2]}=********"
    }
}

fun String?.maskIfSensitive(fieldName: String): String {
    if (this == null) return ""

    val sensitiveFields = listOf("password", "account", "ssn", "token", "credential")

    val isSensitive = sensitiveFields.any { fieldName.contains(it, ignoreCase = true) }

    return if (isSensitive) "********" else this
}

fun String.checkBlank(label: String) {
    if (this.isBlank()) {
        throw BusinessException(
            UserErrorCode.INVALID_VALUE,
            "$label 은 비어있을 수 없습니다."
        )
    }
}

fun String.generateSafeFileName(): String =
    this.substringAfterLast("/")
        .substringAfterLast("\\")
        .replace(Regex("[^A-Za-z0-9._-]"), "_")
        .ifBlank { throw BusinessException(StorageErrorCode.NOT_ALLOW_EXTENSION) }