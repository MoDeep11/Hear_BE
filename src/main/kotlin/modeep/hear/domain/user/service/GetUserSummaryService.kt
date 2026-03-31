package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.`in`.GetUserSummaryUseCase
import modeep.hear.domain.user.port.out.MonthlyStatisticPort
import modeep.hear.domain.user.port.out.query.QueryUserStatPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserSummaryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class GetUserSummaryService(
    private val monthlyStatisticPort: MonthlyStatisticPort,
    private val queryUserStatPort: QueryUserStatPort,
    private val securityPort: SecurityPort,
    private val getEmotionDistributionService: GetEmotionDistributionService
) : GetUserSummaryUseCase {
    override fun execute(): UserSummaryResponse {
        val yearMonth = YearMonth.now()

        val user = securityPort.getCurrentUser()
        val stat = queryUserStatPort.findByUserId(user.id)
            ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)
        val monthlyStat = monthlyStatisticPort.findByUserIdAndYearMonth(user.id, yearMonth)
            ?: throw BusinessException(UserErrorCode.MONTHLY_STATISTIC_NOT_FOUND)

        val emotionDistribution = getEmotionDistributionService.execute(user.id, yearMonth)

        return UserSummaryResponse(
            currentStreak = stat.currentStreak,
            monthlyDiaryCount = monthlyStat.diaryCount,
            emotionDistribution = emotionDistribution
        )
    }
}
