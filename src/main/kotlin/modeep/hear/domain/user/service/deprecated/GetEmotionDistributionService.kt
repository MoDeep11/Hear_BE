package modeep.hear.domain.user.service.deprecated

import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.common.vo.EmotionDistribution
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import java.time.YearMonth
import java.util.UUID

@Deprecated("Use UpdateMonthlyStatisticComponent instead")
class GetEmotionDistributionService(
    private val queryDiaryPort: QueryDiaryPort
) {
    fun execute(
        userId: UUID,
        yearMonth: YearMonth
    ): EmotionDistribution {
        val monthlyDiaries = queryDiaryPort.findAllByUserIdAndYearMonth(userId, yearMonth)
        val emotions: List<Emotion> = monthlyDiaries.map { it.emotion }
        return EmotionDistribution.create(
            emotions.groupingBy { it }.eachCount()
        )
    }
}
