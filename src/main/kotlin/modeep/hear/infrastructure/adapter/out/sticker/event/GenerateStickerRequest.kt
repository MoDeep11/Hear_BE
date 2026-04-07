package modeep.hear.infrastructure.adapter.out.sticker.event

import modeep.hear.domain.common.vo.Emotion
import java.util.UUID

data class GenerateStickerRequest(
    val userId: UUID,
    val diaryId: UUID,
    val emotion: Emotion,
    val content: String
)
