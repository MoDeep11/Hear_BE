package modeep.hear.infrastructure.adapter.out.chat.external.dto.vo

import modeep.hear.domain.user.model.UserStat
import java.util.UUID

data class UserInfo(
    val userId: UUID,
    val nickname: String,
    val streakDays: Int = 0,
    val totalDiaries: Int = 0,
    val maxStreak: Int = 0
) {
    companion object {
        fun of(
            userId: UUID,
            nickname: String,
            userStat: UserStat
        ) = UserInfo(
            userId = userId,
            nickname = nickname,
            streakDays = userStat.currentStreak,
            totalDiaries = userStat.totalDiaries,
            maxStreak = userStat.maxStreak
        )
    }
}