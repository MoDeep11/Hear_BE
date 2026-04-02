package modeep.hear.infrastructure.adapter.`in`.diary.dto.response

import modeep.hear.domain.common.vo.Emotion
import java.time.LocalDate
import java.util.UUID

data class RecommendDiaryResponse(
    val diaryId: UUID,
    val targetDate: LocalDate,
    val emotion: Emotion,
    val recommendationText: String
)