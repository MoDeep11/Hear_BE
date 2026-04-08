package modeep.hear.infrastructure.adapter.out.diary.persistence.entity

import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import modeep.hear.global.common.entity.BaseTimeEntity
import modeep.hear.infrastructure.adapter.out.diary.persistence.entity.id.DiaryStickerIdEntity
import modeep.hear.infrastructure.adapter.out.diary.persistence.entity.id.StickerPlacementJpaEntity

@Entity
@Table(name = "diary_stickers")
class DiaryStickerJpaEntity(

    @EmbeddedId
    val id: DiaryStickerIdEntity,

    @Embedded
    val placement: StickerPlacementJpaEntity
) : BaseTimeEntity()
