package modeep.hear.domain.user.model

import modeep.hear.domain.common.model.base.BaseTime
import java.util.UUID

data class UserStat(
    val userId: UUID? = null,
    val currentStreak: Int,
    val totalDiaries: Int,
    val maxStreak: Int,
    val lastWrittenAt: Long,
    val baseTime: BaseTime
)
