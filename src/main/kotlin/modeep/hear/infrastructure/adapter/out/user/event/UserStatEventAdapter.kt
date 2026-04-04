package modeep.hear.infrastructure.adapter.out.user.event

import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.domain.user.event.DecreasedUserStatEvent
import modeep.hear.domain.user.event.IncreasedUserStatEvent
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.UserCalendar
import modeep.hear.domain.user.model.id.UserCalendarId
import modeep.hear.domain.user.port.out.UserCalendarPort
import modeep.hear.domain.user.port.out.UserStatPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

@Component
class UserStatEventAdapter(
    private val userStatPort: UserStatPort,
    private val queryDiaryPort: QueryDiaryPort,
    private val updateMonthlyStatisticComponent: UpdateMonthlyStatisticComponent,
    private val userCalendarPort: UserCalendarPort
) {
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun onDiaryDeleted(event: DecreasedUserStatEvent) {
        val userId = event.userId
        val now = event.now

        val userStat = userStatPort.findByUserId(userId)
            ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)

        // 지운 일기가 오늘 작성한 일기가 아니거나 오늘 쓴 일기가 남아있을 경우
        if (event.createdAtOfDiary.toLocalDate() != now.toLocalDate() || event.hasTodayDiary) {
            val totalCount = event.totalCount

            val updatedStat = userStat.updateTotalCountOnly(totalCount)
            userStatPort.save(updatedStat)

            updateCalendarOnDelete(userId, event.diaryDate, event.hasTodayDiary)
            return
        }

        val fetchLimit = (userStat.currentStreak + 20).coerceAtLeast(100)
        val recentDates = queryDiaryPort.findDistinctDatesByUserId(event.userId, fetchLimit)

        val newStreak = CurrentStreakCalculator.calculate(recentDates, now.toLocalDate())

        val userStatDecreased = userStat.decreaseDiaryCount(
            totalDiaries = event.totalCount,
            latestWrittenAt = recentDates.firstOrNull(),
            calculatedStreak = newStreak
        )

        userStatPort.save(userStatDecreased)

        updateCalendarOnDelete(userId, event.diaryDate, event.hasTodayDiary)

        val yearMonth = YearMonth.from(event.createdAtOfDiary)
        updateMonthlyStatisticComponent.execute(userId, yearMonth)
    }

    @EventListener
    fun onDiaryCreated(event: IncreasedUserStatEvent) {
        val userId = event.userId

        val userStat = userStatPort.findByUserId(userId)
            ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)

        val userStatIncreased = userStat.increaseDiaryCount(LocalDate.now())

        userStatPort.save(userStatIncreased)

        userCalendarPort.save(
            UserCalendar(
                id = UserCalendarId(calendarDate = event.diaryDate, userId = userId),
                hasDiary = true,
                diaryId = event.diaryId,
                emotion = event.emotion
            )
        )

        val yearMonth = YearMonth.now()
        updateMonthlyStatisticComponent.execute(userId, yearMonth)
    }

    private fun updateCalendarOnDelete(userId: UUID, diaryDate: LocalDate, hasDiaryOnDate: Boolean) {
        if (hasDiaryOnDate) return

        val existingCalendar = userCalendarPort.findByUserIdAndDate(userId, diaryDate) ?: return
        userCalendarPort.save(
            existingCalendar.copy(
                hasDiary = false,
                diaryId = null,
                emotion = null
            )
        )
    }
}
