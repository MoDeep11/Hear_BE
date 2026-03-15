package modeep.hear.domain.diary.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.diary.vo.StickerPlacement
import java.util.UUID

data class DiarySticker(
    val id: UUID? = null,
    val diaryId: UUID? = null,
    val stickerId: UUID? = null,
    val stickerPlacement: StickerPlacement,
    val baseTime: BaseTime
)
