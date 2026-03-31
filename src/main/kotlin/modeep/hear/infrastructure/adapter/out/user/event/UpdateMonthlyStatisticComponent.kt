package modeep.hear.infrastructure.adapter.out.user.event

import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.common.vo.EmotionDistribution
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.domain.user.port.out.MonthlyStatisticPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth
import java.util.UUID

@Component
@Transactional
class UpdateMonthlyStatisticComponent(
    private val queryDiaryPort: QueryDiaryPort,
    private val monthlyStatisticPort: MonthlyStatisticPort
) {
    fun execute(userId: UUID, yearMonth: YearMonth) {
        val monthlyDiaries = queryDiaryPort.findAllByUserIdAndYearMonth(userId, yearMonth)
        val diaryCount = monthlyDiaries.size
        val photoCount = queryDiaryPort.countByUserIdAndYearMonthWithAiImage(userId, yearMonth)
        val emotionDistribution = getEmotionDistribution(userId, yearMonth)

        val monthlyStat = monthlyStatisticPort.findByUserIdAndYearMonth(userId, yearMonth)
        val updated = monthlyStat.copy(
            diaryCount = diaryCount,
            photoCount = photoCount,
            emotionDistribution = emotionDistribution
        )
        monthlyStatisticPort.save(updated)
    }

    private fun getEmotionDistribution(
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