package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.common.vo.EmotionDistribution
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.`in`.GetUserSummaryUseCase
import modeep.hear.domain.user.port.out.query.QueryUserStatPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserSummaryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class GetUserSummaryService(
    private val queryUserStatPort: QueryUserStatPort,
    private val queryDiaryPort: QueryDiaryPort,
    private val securityPort: SecurityPort
) : GetUserSummaryUseCase {
    override fun execute(): UserSummaryResponse {
        val user = securityPort.getCurrentUser()
        val stat = queryUserStatPort.findByUserId(user.id)
            ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)

        val nowYearMonth = YearMonth.now()
        val monthlyDiaries = queryDiaryPort.findAllByUserIdAndYearMonth(user.id, nowYearMonth)

        val emotions: List<Emotion> = monthlyDiaries.map { it.emotion }

        val emotionDistribution = EmotionDistribution.Companion.create(
            emotions.groupingBy { it }.eachCount()
        )

        return UserSummaryResponse(
            currentStreak = stat.currentStreak,
            monthlyDiaryCount = monthlyDiaries.size,
            emotionDistribution = emotionDistribution
        )
    }
}