package modeep.hear.infrastructure.adapter.out.user.event

import modeep.hear.domain.diary.event.DiaryCreatedEvent
import modeep.hear.domain.diary.event.DiaryDeletedEvent
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.UserStatPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UserStatEventAdapter(
    private val userStatPort: UserStatPort,
    private val queryDiaryPort: QueryDiaryPort
) {
    @EventListener
    fun onDiaryDeleted(event: DiaryDeletedEvent) {
        val userId = event.userId

        val userStat = userStatPort.findByUserId(userId)
            ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)
        val now = LocalDate.now()

        // 지운 일기가 오늘 작성한 일기가 아니거나 오늘 쓴 일기가 남아있을 경우
        if (event.deletedTime != now || queryDiaryPort.existsByUserIdAndDate(userId, now)) {
            val totalCount = queryDiaryPort.countByUserId(userId)

            val updatedStat = userStat.updateTotalCountOnly(totalCount.toInt())
            userStatPort.save(updatedStat)
            return
        }

        val fetchLimit = (userStat.currentStreak + 10).coerceAtMost(100)
        val recentDates = queryDiaryPort.findRecentDatesByUserId(userId, fetchLimit)

        val newStreak = CurrentStreakCalculator.calculate(recentDates, now)

        val userStatDecreased = userStat.decreaseDiaryCount(
            totalDiaries = queryDiaryPort.countByUserId(userId).toInt(),
            latestWrittenAt = recentDates.firstOrNull(),
            calculatedStreak = newStreak,
        )

        userStatPort.save(userStatDecreased)
    }

    @EventListener
    fun onDiaryCreated(event: DiaryCreatedEvent) {
        val userId = event.userId

        val userStat = userStatPort.findByUserId(userId)
            ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)

        val userStatIncreased = userStat.increaseDiaryCount(LocalDate.now())

        userStatPort.save(userStatIncreased)
    }
}

// 인덱스 걸어서 조회 속도 높이기
// CREATE INDEX idx_user_diary_created ON diaries (user_id, created_at DESC);
