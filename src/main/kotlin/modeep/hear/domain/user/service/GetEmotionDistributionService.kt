package modeep.hear.domain.user.service

import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.common.vo.EmotionDistribution
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth
import java.util.UUID

@Service
@Transactional(readOnly = true)
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
