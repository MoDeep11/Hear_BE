package modeep.hear.domain.common.vo

import modeep.hear.global.util.EmotionUtil

data class EmotionDistribution(
    val values: Map<Emotion, Double>
) {
    init {
        // require: IllegalArgumentException 반환
        EmotionUtil.validateValues(values.values)
    }

    fun getScore(emotion: Emotion): Double = values.getOrDefault(emotion, 0.0)

    companion object {
        // KEYS.associateWith { VALUE }: Map 생성
        fun empty() = EmotionDistribution(Emotion.entries.associateWith { 0.0 })

        fun create(counts: Map<Emotion, Int>): EmotionDistribution {
            val calculated = EmotionUtil.calculatePercentages(counts)
            return EmotionDistribution(calculated)
        }
    }
}
