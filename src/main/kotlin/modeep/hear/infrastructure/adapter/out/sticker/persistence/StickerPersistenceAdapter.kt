package modeep.hear.infrastructure.adapter.out.sticker.persistence

import modeep.hear.domain.sticker.model.Sticker
import modeep.hear.domain.sticker.port.out.StickerPort
import modeep.hear.infrastructure.adapter.out.sticker.persistence.mapper.StickerMapper
import modeep.hear.infrastructure.adapter.out.sticker.persistence.repository.StickerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class StickerPersistenceAdapter(
    private val repo: StickerRepository,
    private val mapper: StickerMapper
) : StickerPort {
    override fun find(stickerId: UUID): Sticker? {
        return repo.findByIdOrNull(stickerId) ?.let { mapper.toModel(it) }
    }

    override fun findAllByUserId(userId: UUID): List<Sticker> {
        return repo.findAllByUserId(userId).map { mapper.toModel(it) }
    }

    override fun save(sticker: Sticker) {
        val isExist = repo.existsById(sticker.id)
        val entity = mapper.toEntity(sticker, isNew = !isExist)
        repo.save(entity)
    }

    override fun delete(stickerId: UUID) {
        TODO("Not yet implemented")
    }
}
