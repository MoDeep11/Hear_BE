package modeep.hear.domain.user.vo

data class UserInfo(
    val userId: Long,
    val nickname: String,
    val streakDays: Int,
    val totalDiaries: Int,
    val maxStreak: Int
)