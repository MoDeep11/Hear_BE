package modeep.hear.infrastructure.adapter.`in`.user.dto.response

import modeep.hear.domain.common.vo.EmotionDistribution
import java.time.LocalDateTime

data class UserStatisticsResponse(
    val targetYearMonth: String,
    val diaryCount: Int,
    val photoCount: Int,
    val writingRate: Double,
    val aiReportContent: String,
    val emotionDistribution: EmotionDistribution,
    val createdAt: LocalDateTime
)