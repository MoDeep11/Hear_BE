package modeep.hear.domain.user.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.common.vo.EmotionDistribution
import java.time.YearMonth
import java.util.UUID

data class MonthlyStatistics(
    val id: UUID? = null,
    val userId: UUID? = null,
    val targetYearMonth: YearMonth,
    val diaryCount: Int,
    val photoCount: Int,
    val writingRate: Float,
    val aiReportContent: String,
    val emotionDistribution: EmotionDistribution,
    val baseTime: BaseTime
)
