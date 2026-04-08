package modeep.hear.domain.sticker.event

import java.util.UUID

data class CreateStickerEvent(
    val userId: UUID,
    val diaryId: UUID
)
