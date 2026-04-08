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
            diaryId = entity.diaryId,
            status = entity.status,
            imageUrl = entity.imageUrl,
            keyword = entity.keyword,
            baseTime = baseTimeMapper.toModel(entity.baseTime)
        )
    }

    fun toEntity(model: Sticker, isNew: Boolean): StickerJpaEntity {
        val entity = StickerJpaEntity(
            id = model.id,
            userId = model.userId,
            diaryId = model.diaryId,
            status = model.status,
            imageUrl = model.imageUrl,
            keyword = model.keyword
        )
        if (!isNew) {
            entity.markNotNew()
        }
        return entity
    }
}
