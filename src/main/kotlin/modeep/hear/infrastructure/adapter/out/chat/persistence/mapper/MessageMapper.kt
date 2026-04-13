package modeep.hear.infrastructure.adapter.out.chat.persistence.mapper

import modeep.hear.domain.chat.model.Message
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.entity.MessageJpaEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MessageMapper(
    private val baseTimeMapper: BaseTimeMapper
) {
    fun toModel(chatId: UUID, entity: MessageJpaEntity): Message = Message(
        id = entity.id,
        chatId = chatId,
        sender = entity.sender,
        message = entity.message,
        messageType = entity.messageType,
        voiceUrl = entity.voiceUrl,
        baseTime = baseTimeMapper.toModel(entity.baseTime)
    )

    fun toEntity(model: Message, isNew: Boolean): MessageJpaEntity {
        val entity = MessageJpaEntity(
            id = model.id,
            chatId = model.chatId,
            sender = model.sender,
            message = model.message,
            messageType = model.messageType,
            voiceUrl = model.voiceUrl
        )
        if (!isNew) {
            entity.markNotNew()
        }
        return entity
    }
}
