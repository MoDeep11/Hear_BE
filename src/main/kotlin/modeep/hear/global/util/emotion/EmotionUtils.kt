package modeep.hear.global.util.emotion

import kotlin.math.abs
import kotlin.math.round

object EmotionUtils {

    fun Double.roundToOneDecimal(): Double =
        round(this * 10) / 10.0

    fun calculatePercentages(counts: Map<String, Int>): Map<String, Double> {
        val totalCount = counts.values.sum().toDouble()

        if (totalCount == 0.0) {
            return counts.mapValues { 0.0 }
        }

        val percentages = counts.mapValues { (_, count) ->
            (count / totalCount * 100).roundToOneDecimal()
        }.toMutableMap()

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