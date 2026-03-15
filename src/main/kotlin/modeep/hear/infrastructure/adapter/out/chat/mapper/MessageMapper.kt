package modeep.hear.infrastructure.adapter.out.chat.mapper

import modeep.hear.domain.chat.model.Message
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.chat.entity.MessageJpaEntity
import org.mapstruct.Mapper

@Mapper(
    componentModel = "spring",
    uses = [BaseTimeMapper::class]
)
interface MessageMapper {
    fun toModel(entity: MessageJpaEntity): Message

    fun toEntity(model: Message): MessageJpaEntity
}
