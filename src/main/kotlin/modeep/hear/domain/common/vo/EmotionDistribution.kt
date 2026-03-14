package modeep.hear.domain.common.vo

import modeep.hear.global.util.emotion.EmotionUtils

data class EmotionDistribution(
    val values: Map<Emotion, Double>
) {
    init {
        // require: IllegalArgumentException 반환
        EmotionUtils.validateValues(values.values)
    }

    fun getScore(emotion: Emotion): Double = values.getOrDefault(emotion, 0.0)

    companion object {
        // Emotion.values() -> Emotion.entries
        // KEYS.associateWith { VALUE }: Map 생성
        fun emptyAllZero() = EmotionDistribution(Emotion.entries.associateWith { 0.0 })

        fun create(counts: Map<Emotion, Int>): EmotionDistribution {
            val emotionName = counts.mapKeys { it.key.name }
            val calculated = EmotionUtils.calculatePercentages(emotionName)

            val finalValues = calculated.mapKeys { (key, _) -> Emotion.valueOf(key) }
            return EmotionDistribution(finalValues)
        }
    }
}
