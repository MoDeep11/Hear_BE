package modeep.hear.infrastructure.adapter.out.diary.persistence.entity.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class DiaryStickerIdEntity(
    @Column(name = "diary_id")
    val diaryId: UUID,

    @Column(name = "sticker_id")
    val stickerId: UUID
)
