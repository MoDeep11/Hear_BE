package modeep.hear.infrastructure.adapter.out.diary.external.dto.request

import modeep.hear.domain.common.vo.Emotion
import java.util.UUID

data class GenerateImageRequest(
    val diaryId: UUID,
    val userId: UUID,
    val emotion: Emotion,
    val content: String
)