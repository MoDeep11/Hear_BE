package modeep.hear.domain.diary.model

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.diary.model.id.DiaryStickerId
import modeep.hear.domain.diary.vo.StickerPlacement
import modeep.hear.domain.sticker.model.Sticker
import java.util.UUID

data class DiarySticker(
    val id: DiaryStickerId,
    val placement: StickerPlacement,
    val baseTime: BaseTime
) {
    companion object {
        fun create(diaryId: UUID, sticker: Sticker): DiarySticker {
            return DiarySticker(
                id = DiaryStickerId(diaryId, sticker.id),
                placement = StickerPlacement(),
                baseTime = BaseTime()
            )
        }
    }
}
