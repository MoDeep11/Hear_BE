package modeep.hear.infrastructure.adapter.out.chat.mapper

import modeep.hear.domain.chat.model.Chat
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.chat.entity.ChatJpaEntity
import org.mapstruct.Mapper

@Mapper(
    componentModel = "spring",
    uses = [BaseTimeMapper::class]
)
interface ChatMapper {
    fun toModel(entity: ChatJpaEntity): Chat

    fun toEntity(model: Chat): ChatJpaEntity
}
