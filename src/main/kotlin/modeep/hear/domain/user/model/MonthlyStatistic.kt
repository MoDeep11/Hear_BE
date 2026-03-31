package modeep.hear.domain.user.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.common.vo.EmotionDistribution
import modeep.hear.global.util.calculateWritingRate
import modeep.hear.global.util.validateNotNegative
import java.time.YearMonth
import java.util.UUID

data class MonthlyStatistic(
    val userId: UUID,
    val targetYearMonth: YearMonth,
    val diaryCount: Int = 0,
    val photoCount: Int = 0,
    val aiReportContent: String? = null,
    val emotionDistribution: EmotionDistribution = EmotionDistribution.empty(),
    val baseTime: BaseTime
) {
    init {
        diaryCount.validateNotNegative("diaryCount")
        photoCount.validateNotNegative("photoCount")
    }

    val writingRate: Float
        get() = targetYearMonth.calculateWritingRate(diaryCount)

    companion object {
        fun create(
            userId: UUID,
            targetYearMonth: YearMonth
        ): MonthlyStatistic {
            return MonthlyStatistic(
                userId = userId,
                targetYearMonth = targetYearMonth,
                baseTime = BaseTime()
            )
        }
    }
}
