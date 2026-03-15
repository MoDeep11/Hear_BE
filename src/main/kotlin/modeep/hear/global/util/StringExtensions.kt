package modeep.hear.global.util

fun String.maskUri(): String {
    val sensitiveKeys = listOf("ServiceKey", "accessToken", "auth", "token")

    var maskedUri = this
    sensitiveKeys.forEach { key ->
        val regex = Regex("($key=)[^&]*")
        maskedUri = maskedUri.replace(regex, "$1********")
    }
    return maskedUri
}

fun String.maskSafe(target: String): String {
    val index = indexOf(target)
    if (index == -1) return this // 대상이 없으면 원본 반환
    return replaceRange(index, index + target.length, "***")
}
