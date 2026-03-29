package modeep.hear.infrastructure.adapter.out.chat.persistence.mapper

import modeep.hear.domain.chat.model.Chat
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.entity.ChatJpaEntity
import org.springframework.stereotype.Component

@Component
class ChatMapperImpl(
    private val baseTimeMapper: BaseTimeMapper,
    private val messageMapper: MessageMapper
) : ChatMapper {
    override fun toModel(entity: ChatJpaEntity): Chat =
        Chat(
            id = entity.id,
            userId = entity.userId,
            status = entity.status,
            baseTime = baseTimeMapper.toModel(entity.baseTime),
            messages = entity.messages
                .map { messageMapper.toModel(entity.id, it) }
                .toMutableList()
        )

    override fun toEntity(model: Chat): ChatJpaEntity {
        val chatEntity = ChatJpaEntity(
            id = model.id,
            userId = model.userId,
            status = model.status
        )

        val messageEntities = model.messages.map {
            messageMapper.toEntity(chatEntity, it)
        }

        chatEntity.updateMessages(messageEntities)

        return chatEntity
    }
}
