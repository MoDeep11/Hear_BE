package modeep.hear.infrastructure.adapter.out.statistic.external.dto.request

import modeep.hear.domain.diary.model.Diary
import java.time.YearMonth
import java.util.UUID

data class GenerateReportRequest(
    val userId: UUID,
    val yearMonth: YearMonth,
    val diaries: List<Diary>,
    val monthlyDiaryCount: Int,
    val monthlyPhotoCount: Int,
    val totalDiaries: Int,
    val maxStreak: Int,
    val currentStreak: Int
)
