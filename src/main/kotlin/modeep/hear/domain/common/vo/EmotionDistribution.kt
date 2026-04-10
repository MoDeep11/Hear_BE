package modeep.hear.domain.common.vo

data class EmotionDistribution(
    val values: Map<Emotion, Int>
) {
    fun getScore(emotion: Emotion): Int = values.getOrDefault(emotion, 0)

    companion object {
        fun empty() = EmotionDistribution(Emotion.entries.associateWith { 0 })

        fun create(counts: Map<Emotion, Int>): EmotionDistribution {
            return EmotionDistribution(counts)
        }
    }
}
