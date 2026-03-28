package modeep.hear.domain.user.model

import modeep.hear.domain.common.vo.BaseTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class UserStat(
    val userId: UUID,
    val currentStreak: Int = 0,
    val totalDiaries: Int = 0,
    val maxStreak: Int = 0,
    val lastWrittenAt: LocalDate? = null,
    val baseTime: BaseTime
) {
    companion object {
        fun create(userId: UUID): UserStat = UserStat(
            userId = userId,
            baseTime = BaseTime()
        )
    }

    fun increaseDiaryCount(now: LocalDate): UserStat {

        // 연속 작성 여부 확인
        val newStreak = when {
            lastWrittenAt == null -> 1 // 처음 쓰는 경우
            isToday(now = now) -> currentStreak // 오늘 이미 작성했을 경우 스트릭 유지
            isYesterday(now = now) -> currentStreak + 1 // 연속 스트릭
            else -> 1 // 스트릭 초기화
        }

        return this.copy(
            totalDiaries = this.totalDiaries + 1,
            currentStreak = newStreak,
            maxStreak = if (newStreak > maxStreak) newStreak else maxStreak,
            lastWrittenAt = now
        )
    }

    fun decreaseDiaryCount(totalDiaries: Int, previousLastWrittenAt: LocalDate?): UserStat {
        if (previousLastWrittenAt == null || totalDiaries == 0) {
            return this.copy(
                totalDiaries = 0,
                currentStreak = 0,
                lastWrittenAt = null
            )
        }

        val now = LocalDate.now()

        val newStreak = when {
            isToday(previousLastWrittenAt, now) -> this.currentStreak // 오늘 기록이 여전히 있음 (유지)
            isYesterday(previousLastWrittenAt, now) -> this.currentStreak // 어제 기록이 최신임 (유지)
            else -> 0 // 그보다 전이 최신이면 스트릭은 끊긴 상태임
        }

        return this.copy(
            totalDiaries = totalDiaries,
            lastWrittenAt = previousLastWrittenAt,
            currentStreak = newStreak
        )
    }

    // 오늘 이미 일기를 썼는지 확인
    private fun isToday(target: LocalDate? = this.lastWrittenAt, now: LocalDate): Boolean =
        target == now

    // 마지막 작성일이 어제인지 확인
    private fun isYesterday(target: LocalDate? = this.lastWrittenAt, now: LocalDate): Boolean {
        val date = target ?: return false
        return date.plusDays(1) == now
    }

    fun update(
        currentStreak: Int = this.currentStreak,
        totalDiaries: Int = this.totalDiaries,
        maxStreak: Int = this.maxStreak,
        lastWrittenAt: LocalDate? = this.lastWrittenAt
    ) : UserStat {
        return this.copy(
            currentStreak = currentStreak,
            totalDiaries = totalDiaries,
            maxStreak = maxStreak,
            lastWrittenAt = lastWrittenAt
        )
    }
}
