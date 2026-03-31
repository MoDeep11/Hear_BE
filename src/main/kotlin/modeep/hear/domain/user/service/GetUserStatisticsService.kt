package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.`in`.GetUserStatisticsUseCase
import modeep.hear.domain.user.port.out.MonthlyStatisticPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserStatisticsResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class GetUserStatisticsService(
    private val monthlyStatisticPort: MonthlyStatisticPort,
    private val securityPort: SecurityPort,
    private val getEmotionDistributionService: GetEmotionDistributionService
) : GetUserStatisticsUseCase {
    override fun execute(
        yearMonth: YearMonth
    ): UserStatisticsResponse {
        val user = securityPort.getCurrentUser()
        val monthlyStat = monthlyStatisticPort.findByUserIdAndYearMonth(user.id, yearMonth)

        val emotionDistribution = getEmotionDistributionService.execute(user.id, yearMonth)

        return UserStatisticsResponse(
            targetYearMonth = yearMonth,
            diaryCount = monthlyStat.diaryCount,
            photoCount = monthlyStat.photoCount,
            writingRate = monthlyStat.writingRate,
            aiReportContent = monthlyStat.aiReportContent,
            emotionDistribution = emotionDistribution,
            createdAt = user.baseTime.createdAt
        )
    }
}
