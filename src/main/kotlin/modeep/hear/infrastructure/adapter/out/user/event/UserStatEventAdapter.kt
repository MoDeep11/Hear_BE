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

        val latestDiary = queryDiaryPort.findTopByUserIdOrderByCreatedAtDesc(userId)
        val totalCount = queryDiaryPort.countByUserId(userId)

        val lastWrittenAt = latestDiary?.baseTime?.createdAt

        val userStatDecreased = userStat.decreaseDiaryCount(
            totalDiaries = totalCount.toInt(),
            previousLastWrittenAt = lastWrittenAt?.toLocalDate()
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
