package modeep.hear.domain.user.model

import modeep.hear.domain.common.vo.BaseTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class UserStat(
    val userId: UUID? = null,
    val currentStreak: Int = 0,
    val totalDiaries: Int = 0,
    val maxStreak: Int = 0,
    val lastWrittenAt: LocalDateTime? = null,
    val baseTime: BaseTime
) {
    fun increaseDiaryCount(now: LocalDateTime): UserStat {
        // 연속 작성 여부 확인
        val newStreak = when {
            lastWrittenAt == null -> 1 // 처음 쓰는 경우
            isToday(now) -> currentStreak // 오늘 작성했을 경우 스트릭 유지
            isYesterday(now) -> currentStreak + 1 // 연속 스트릭
            else -> 1 // 스트릭 초기화
        }

        return this.copy(
            totalDiaries = this.totalDiaries + 1,
            currentStreak = newStreak,
            maxStreak = if (newStreak > maxStreak) newStreak else maxStreak,
            lastWrittenAt = now
        )
    }

    // 일기 삭제 시
    // 단, 정책 상 일기 삭제 시에도 streak은 유지되도록 한다.
    fun decreaseDiaryCount(previousLastWrittenAt: LocalDateTime?): UserStat {
        if (this.totalDiaries <= 0) return this

        return if (previousLastWrittenAt == null) {
            this.copy(
                totalDiaries = 0,
                lastWrittenAt = null
            )
        } else {
            this.copy(
                totalDiaries = this.totalDiaries - 1,
                lastWrittenAt = previousLastWrittenAt,
            )
        }
    }

    // 오늘 이미 일기를 썼는지 확인
    private fun isToday(now: LocalDateTime): Boolean {
        return lastWrittenAt?.toLocalDate() == now.toLocalDate()
    }

    // 마지막 작성일이 어제인지 확인
    private fun isYesterday(now: LocalDateTime): Boolean {
        val lastDate = lastWrittenAt?.toLocalDate() ?: return false
        val today = now.toLocalDate()
        return lastDate.plusDays(1) == today
    }
}
