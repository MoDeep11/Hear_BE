package modeep.hear.infrastructure.adapter.out.chat.mapper

import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.model.Message
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.chat.entity.ChatJpaEntity
import modeep.hear.infrastructure.adapter.out.chat.entity.MessageJpaEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ChatMapperImpl(
    private val baseTimeMapper: BaseTimeMapper
) : ChatMapper {
    override fun toModel(entity: ChatJpaEntity): Chat =
        Chat(
            id = entity.id,
            userId = entity.userId,
            status = entity.status,
            baseTime = baseTimeMapper.toModel(entity.baseTime),
            messages = entity.messages
                .map { msg -> msg.toModel(entity.id) }
                .toMutableList()
        )

    override fun toEntity(model: Chat): ChatJpaEntity {
        val chatEntity = ChatJpaEntity(
            id = model.id,
            userId = model.userId,
            status = model.status
        )

        val messageEntities = model.messages.map { msg ->
            msg.toEntity(chatEntity)
        }

        chatEntity.updateMessages(messageEntities)

        return chatEntity
    }

    private fun MessageJpaEntity.toModel(chatId: UUID): Message = Message(
        id = this.id,
        chatId = chatId,
        sender = this.sender,
        message = this.message,
        messageType = this.messageType,
        voiceUrl = this.voiceUrl,
        duration = this.duration,
        baseTime = baseTimeMapper.toModel(this.baseTime)
    )

    private fun Message.toEntity(chat: ChatJpaEntity): MessageJpaEntity = MessageJpaEntity(
        id = this.id,
        chat = chat,
        sender = this.sender,
        message = this.message,
        messageType = this.messageType,
        voiceUrl = this.voiceUrl,
        duration = this.duration
    )
}
