package modeep.hear.domain.common.model

import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.util.emotion.EmotionUtils

data class EmotionDistribution(
    val values: Map<Emotion, Double>
) {
    init {
        require(values.values.all { it in 0.0..100.0 }) {
            "감정 수치는 0에서 100 사이여야 합니다."
        }

        require(values.values.all { it * 10 == kotlin.math.floor(it * 10) }) {
            throw BusinessException("감정 수치는 소수점 첫째 자리까지만 허용됩니다.")
        }
    }

    fun getScore(emotion: Emotion): Double = values.getOrDefault(emotion, 0.0)

    companion object {
        fun empty(defaultEmotion: Emotion) = EmotionDistribution(mapOf(defaultEmotion to 100.0))

        fun fromCounts(counts: Map<Emotion, Int>): EmotionDistribution {
            val emotionName = counts.mapKeys { it.key.name }
            val calculated = EmotionUtils.calculatePercentages(emotionName)

            val finalValues = calculated.mapKeys { (key, _) -> Emotion.valueOf(key) }
            return EmotionDistribution(finalValues)
        }
    }
}