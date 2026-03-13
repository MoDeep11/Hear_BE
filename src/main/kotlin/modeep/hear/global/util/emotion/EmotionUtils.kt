package modeep.hear.global.util.emotion

import modeep.hear.domain.common.exception.CommonErrorCode
import modeep.hear.global.error.exception.BusinessException
import kotlin.math.abs
import kotlin.math.round

object EmotionUtils {
    fun Double.roundToOneDecimal(): Double =
        round(this * 10) / 10.0

    fun validateValues(values: Collection<Double>) {
        if (values.any { it !in 0.0..100.0 }) {
            throw BusinessException(CommonErrorCode.INVALID_EMOTION_EXCEPTION, "감정 수치는 0~100 사이여야 합니다.")
        }
        if (values.any { it * 10 != kotlin.math.floor(it * 10) }) {
            throw BusinessException(CommonErrorCode.INVALID_EMOTION_EXCEPTION, "소수점 첫째 자리까지만 허용됩니다.")
        }
    }

    // 감정 별 퍼센트를 반환하는 함수
    fun calculatePercentages(counts: Map<String, Int>): Map<String, Double> {
        val totalCount = counts.values.sum().toDouble()

        // 0% 반환
        if (totalCount == 0.0) {
            return counts.mapValues { 0.0 }
        }

        // 퍼센트 계산
        val percentages = counts.mapValues { (_, count) ->
            (count / totalCount * 100).roundToOneDecimal()
        }.toMutableMap()

        // 100 보다 부족할 경우, 반올림으로 해결
        val currentTotal = percentages.values.sum()
        val diff = (100.0 - currentTotal).roundToOneDecimal()
        if (abs(diff) > 0.0) {
            val maxKey = counts.maxByOrNull { it.value }?.key
            maxKey?.let {
                percentages[it] = (percentages[it] ?: 0.0) + diff
            }
        }

        return percentages
    }
}
