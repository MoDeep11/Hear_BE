package modeep.hear.global.util

import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.global.error.exception.BusinessException
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.round

fun YearMonth.calculateWritingRate(diaryCount: Int): Float {
    val daysInMonth = this.lengthOfMonth()
    if (daysInMonth <= 0) return 0.0f

    val rawRate = (diaryCount.toFloat() / daysInMonth) * 100
    return rawRate.roundToOneDecimal()
}

fun Float.roundToOneDecimal(): Float {
    return round(this * 10f) / 10f
}

fun Int.validateNotNegative(label: String) {
    if (this < 0) {
        throw BusinessException(
            UserErrorCode.INVALID_VALUE,
            "$label 는 음수일 수 없습니다."
        )
    }
}
