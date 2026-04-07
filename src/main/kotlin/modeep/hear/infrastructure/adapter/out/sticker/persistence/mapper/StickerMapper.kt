package modeep.hear.infrastructure.adapter.out.sticker.persistence.mapper

import modeep.hear.domain.sticker.model.Sticker
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.sticker.persistence.entity.StickerJpaEntity
import org.springframework.stereotype.Component

@Component
class StickerMapper(
    private val baseTimeMapper: BaseTimeMapper
) {
    fun toModel(entity: StickerJpaEntity): Sticker {
        return Sticker(
            id = entity.id,
            userId = entity.userId,
            status = entity.status,
            imageUrl = entity.imageUrl,
            baseTime = baseTimeMapper.toModel(entity.baseTime)
        )
    }

    fun toEntity(model: Sticker, isNew: Boolean): StickerJpaEntity {
        val entity = StickerJpaEntity(
            id = model.id,
            userId = model.userId,
            status = model.status,
            imageUrl = model.imageUrl
        )
        if (!isNew) {
            entity.markNotNew()
        }
        return entity
    }
}
