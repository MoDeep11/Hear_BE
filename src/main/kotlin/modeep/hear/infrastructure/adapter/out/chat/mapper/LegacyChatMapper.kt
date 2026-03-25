package modeep.hear.infrastructure.adapter.out.chat.mapper

import modeep.hear.domain.chat.model.Chat
import modeep.hear.infrastructure.adapter.out.chat.entity.ChatJpaEntity
import org.mapstruct.Mapping

// @Mapper(
//    componentModel = "spring",
//    uses = [BaseTimeMapper::class]
// )
@Deprecated("not used")
interface LegacyChatMapper {
    fun toModel(entity: ChatJpaEntity): Chat

    // baseTime 매핑 무시
    @Mapping(target = "baseTime", ignore = true)
    fun toEntity(model: Chat): ChatJpaEntity
}
