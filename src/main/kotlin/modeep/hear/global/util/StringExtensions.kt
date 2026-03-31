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

fun String.maskEmail(): String {
    if (!this.contains("@")) return this

    val parts = this.split("@")
    val id = parts[0]
    val domain = parts[1]

    val maskedId = when {
        id.length <= 2 -> "*" .repeat(id.length) // 아이디가 2글자 이하일 때
        id.length <= 5 -> id.take(2) + "*".repeat(id.length - 2) // 5글자 이하일 때 (앞 2글자 노출)
        else -> id.take(3) + "*".repeat(id.length - 3) // 그 외 (앞 3글자 노출)
    }

    return "$maskedId@$domain"
}

fun String.checkBlank(label: String) {
    if (this.isBlank()) {
        throw BusinessException(
            UserErrorCode.INVALID_VALUE,
            "$label 은 비어있을 수 없습니다."
        )
    }
}

fun String.generateSafeFileName(): String {
    val safeName = this.substringAfterLast("/")
        .substringAfterLast("\\")
        .replace(Regex("[^A-Za-z0-9._-]"), "_")
        .trim()

    if (safeName.isBlank() || safeName == "." || safeName == "..") {
        throw BusinessException(StorageErrorCode.NOT_ALLOW_EXTENSION)
    }

    return safeName
}
