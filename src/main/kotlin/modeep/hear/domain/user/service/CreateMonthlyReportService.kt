package modeep.hear.domain.user.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import modeep.hear.domain.common.component.GetDataForRequestComponent
import modeep.hear.domain.user.port.`in`.CreateMonthlyReportUseCase
import modeep.hear.domain.user.port.out.MonthlyStatisticPort
import modeep.hear.domain.user.port.out.external.FetchMonthlyStatisticPort
import modeep.hear.infrastructure.adapter.out.statistic.external.dto.request.GenerateReportRequest
import org.springframework.stereotype.Service
import java.time.YearMonth
import java.util.UUID

@Service
class CreateMonthlyReportService(
    private val fetchMonthlyStatisticPort: FetchMonthlyStatisticPort,
    private val monthlyStatisticPort: MonthlyStatisticPort,
    private val getDataForRequestComponent: GetDataForRequestComponent,
    private val commandReportComponent: CommandReportComponent
) : CreateMonthlyReportUseCase {
    override suspend fun execute(userId: UUID) {
        val yearMonth = YearMonth.now()
        val (userInfo, summary) = withContext(Dispatchers.IO) {
            getDataForRequestComponent.getUserInfoWithDiariesSummary(userId, yearMonth)
        }

        val req = GenerateReportRequest(
            userId = userInfo.userId,
            yearMonth = yearMonth,
            diaries = summary.diaryInfos,
            monthlyDiaryCount = summary.monthlyDiaryCount,
            monthlyPhotoCount = summary.monthlyPhotoCount,
            totalDiaries = userInfo.totalDiaries,
            maxStreak = userInfo.maxStreak,
            currentStreak = userInfo.streakDays
        )

        val res = fetchMonthlyStatisticPort.generateReport(req)

        val stat = monthlyStatisticPort.findByUserIdAndYearMonth(userId, yearMonth)
        val updated = stat.updateReport(res.aiReportComment)

        commandReportComponent.save(updated)
    }
}
