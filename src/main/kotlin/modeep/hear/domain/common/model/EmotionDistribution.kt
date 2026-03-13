package modeep.hear.domain.common.model

import modeep.hear.global.util.emotion.EmotionUtils

data class EmotionDistribution(
    val values: Map<Emotion, Double>
) {
    fun getScore(emotion: Emotion): Double = values.getOrDefault(emotion, 0.0)

    companion object {
        fun empty(defaultEmotion: Emotion) = EmotionDistribution(mapOf(defaultEmotion to 100.0))

        fun fromCounts(counts: Map<Emotion, Int>): EmotionDistribution {
            val rawMap = counts.mapKeys { it.key.name }
            val calculated = EmotionUtils.calculatePercentages(rawMap)

            val finalValues = calculated.mapKeys { (key, _) -> Emotion.valueOf(key) }
            return EmotionDistribution(finalValues)
        }
    }
}