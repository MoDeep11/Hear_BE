package modeep.hear.domain.user.service

import modeep.hear.domain.common.component.GetDataForRequestComponent
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.`in`.CreateMonthlyReportUseCase
import modeep.hear.domain.user.port.out.MonthlyStatisticPort
import modeep.hear.domain.user.port.out.external.FetchMonthlyStatisticPort
import modeep.hear.domain.user.port.out.query.QueryUserPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.statistic.external.dto.request.DiaryInfo
import modeep.hear.infrastructure.adapter.out.statistic.external.dto.request.GenerateReportRequest
import org.springframework.stereotype.Service
import java.time.YearMonth
import java.util.UUID

@Service
class CreateMonthlyReportService(
    private val fetchMonthlyStatisticPort: FetchMonthlyStatisticPort,
    private val monthlyStatisticPort: MonthlyStatisticPort,
    private val queryUserPort: QueryUserPort,
    private val queryDiaryPort: QueryDiaryPort,
    private val getDataForRequestComponent: GetDataForRequestComponent,
    private val saveReportComponent: SaveReportComponent
) : CreateMonthlyReportUseCase {
    override suspend fun execute(userId: UUID) {
        val user = queryUserPort.findById(userId) ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
        val userInfo = getDataForRequestComponent.getUserInfoOnly(user)

        val yearMonth = YearMonth.now()
        val diaries = queryDiaryPort.findAllByUserIdAndYearMonth(userId, yearMonth)

        val diaryInfos = diaries.map {
            DiaryInfo.from(it)
        }

        val req = GenerateReportRequest(
            userId = userInfo.userId,
            yearMonth = yearMonth,
            diaries = diaryInfos,
            monthlyDiaryCount = diaries.size,
            monthlyPhotoCount = diaries.sumOf { it.diaryImages.size },
            totalDiaries = userInfo.totalDiaries,
            maxStreak = userInfo.maxStreak,
            currentStreak = userInfo.streakDays
        )

        val res = fetchMonthlyStatisticPort.generateReport(req)

        val stat = monthlyStatisticPort.findByUserIdAndYearMonth(userId, yearMonth)
        val updated = stat.updateReport(res.aiReportComment)

        saveReportComponent.save(updated)
    }
}
