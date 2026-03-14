package modeep.hear.infrastructure.adapter.out.chat.mapper

import modeep.hear.domain.chat.model.Message
import modeep.hear.infrastructure.adapter.out.chat.entity.MessageJpaEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface MessageMapper {
    fun toModel(entity: MessageJpaEntity): Message

    fun toEntity(model: Message): MessageJpaEntity
}
