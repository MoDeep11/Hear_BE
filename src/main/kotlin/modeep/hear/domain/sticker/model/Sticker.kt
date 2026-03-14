package modeep.hear.domain.sticker.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.sticker.vo.StickerStatus
import java.util.UUID

@Aggregate
data class Sticker(
    val id: UUID? = null,
    val userId: UUID? = null,
    val status: StickerStatus = StickerStatus.PENDING,
    val imageUrl: String,
    val baseTime: BaseTime
)
