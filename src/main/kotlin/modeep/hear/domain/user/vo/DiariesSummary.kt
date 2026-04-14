package modeep.hear.domain.user.vo

import modeep.hear.infrastructure.adapter.out.statistic.external.dto.request.DiaryInfo

data class DiariesSummary(
    val diaryInfos: List<DiaryInfo>,
    val monthlyDiaryCount: Int,
    val monthlyPhotoCount: Int
)
