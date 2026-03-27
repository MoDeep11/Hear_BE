package modeep.hear.infrastructure.adapter.out.chat.external.dto.vo

import java.util.UUID

data class UserInfo(
    val userId: UUID,
    val nickname: String,
    val streakDays: Int,
    val totalDiaries: Int,
    val maxStreak: Int
) {
    fun toUserInfo(
        userId: UUID,
        nickname: String,
        streakDays: Int,
        totalDiaries: Int,
        maxStreak: Int
    ) = UserInfo(
        userId = userId,
        nickname = nickname,
        streakDays = streakDays,
        totalDiaries = totalDiaries,
        maxStreak = maxStreak
    )
}