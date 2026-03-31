package modeep.hear.infrastructure.adapter.`in`.user.dto.response

import modeep.hear.domain.common.vo.EmotionDistribution

data class UserSummaryResponse(
    val currentStreak: Int,
    val monthlyDiaryCount: Int,
    val emotionDistribution: EmotionDistribution
)
