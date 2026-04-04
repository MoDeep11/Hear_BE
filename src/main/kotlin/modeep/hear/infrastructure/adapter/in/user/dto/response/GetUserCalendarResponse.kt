package modeep.hear.infrastructure.adapter.`in`.user.dto.response

import modeep.hear.domain.common.vo.Emotion
import java.time.LocalDate
import java.util.UUID

data class GetUserCalendarResponse(
    val date: LocalDate,
    val diaryId: UUID,
    val emotion: Emotion
)
