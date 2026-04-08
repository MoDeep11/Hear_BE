package modeep.hear.infrastructure.adapter.out.sticker.persistence.repository

import modeep.hear.infrastructure.adapter.out.sticker.persistence.entity.StickerJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StickerRepository : JpaRepository<StickerJpaEntity, UUID> {
    fun findAllByUserId(userId: UUID): MutableList<StickerJpaEntity>
}
