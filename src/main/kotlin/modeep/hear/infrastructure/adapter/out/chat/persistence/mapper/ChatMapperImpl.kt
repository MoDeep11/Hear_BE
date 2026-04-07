package modeep.hear.infrastructure.adapter.out.chat.persistence.mapper

import modeep.hear.domain.chat.model.Chat
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.entity.ChatJpaEntity
import org.springframework.stereotype.Component

@Component
class ChatMapperImpl(
    private val baseTimeMapper: BaseTimeMapper
) : ChatMapper {
    override fun toModel(entity: ChatJpaEntity): Chat =
        Chat(
            id = entity.id,
            userId = entity.userId,
            status = entity.status,
            baseTime = baseTimeMapper.toModel(entity.baseTime)
        )

    override fun toEntity(model: Chat, isNew: Boolean): ChatJpaEntity {
        val entity = ChatJpaEntity(
            id = model.id,
            userId = model.userId,
            status = model.status
        )
        if (!isNew) {
            entity.markNotNew()
        }
        return entity
    }
}
