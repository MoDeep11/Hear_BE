package modeep.hear.domain.user.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.common.vo.EmotionDistribution
import modeep.hear.global.util.calculateWritingRate
import java.time.YearMonth
import java.util.UUID

data class MonthlyStatistic(
    val id: UUID? = null,
    val userId: UUID? = null,
    val targetYearMonth: YearMonth,
    val diaryCount: Int = 0,
    val photoCount: Int = 0,
    val aiReportContent: String? = null,
    val emotionDistribution: EmotionDistribution = EmotionDistribution.empty(),
    val baseTime: BaseTime
) {
    val writingRate: Float
        get() = targetYearMonth.calculateWritingRate(diaryCount)
}
