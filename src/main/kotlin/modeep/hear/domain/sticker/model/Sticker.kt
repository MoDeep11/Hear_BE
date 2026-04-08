package modeep.hear.domain.sticker.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.sticker.vo.StickerStatus
import java.util.UUID

@Aggregate
data class Sticker(
    val id: UUID,
    val userId: UUID,
    val diaryId: UUID? = null,
    val status: StickerStatus = StickerStatus.PENDING,
    val imageUrl: String,
    val keyword: String? = null,
    val baseTime: BaseTime
) {
    companion object {
        fun create(
            userId: UUID,
            diaryId: UUID? = null,
            status: StickerStatus,
            imageUrl: String
        ): Sticker {
            return Sticker(
                id = UUID.randomUUID(),
                userId = userId,
                diaryId = diaryId,
                status = status,
                imageUrl = imageUrl,
                baseTime = BaseTime()
            )
        }
    }
}
