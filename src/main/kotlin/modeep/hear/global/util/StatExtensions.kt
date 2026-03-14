package modeep.hear.global.util

import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.round

fun YearMonth.calculateWritingRate(diaryCount: Int): Float {
    val daysInMonth = this.lengthOfMonth()
    if (daysInMonth <= 0) return 0.0f

    val today = LocalDate.now()
    if (YearMonth.of(today.year, today.monthValue) != this) return 0.0f

    today.dayOfMonth

    val rawRate = (diaryCount.toFloat() / daysInMonth) * 100
    return rawRate.roundToOneDecimal()
}

fun Float.roundToOneDecimal(): Float {
    return round(this * 10f) / 10f
}
