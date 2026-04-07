package modeep.hear.domain.sticker.port.out

import modeep.hear.domain.sticker.model.Sticker
import java.util.UUID

interface StickerPort {
    fun find(stickerId: UUID): Sticker?

    fun findAllByUserId(userId: UUID): List<Sticker>

    fun save(sticker: Sticker)

    fun delete(stickerId: UUID)
}
