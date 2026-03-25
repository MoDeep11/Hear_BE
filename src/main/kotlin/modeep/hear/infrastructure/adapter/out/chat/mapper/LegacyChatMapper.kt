package modeep.hear.infrastructure.adapter.out.chat.mapper

import modeep.hear.domain.chat.model.Chat
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.chat.entity.ChatJpaEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Deprecated("not used")
//@Mapper(
//    componentModel = "spring",
//    uses = [BaseTimeMapper::class]
//)
interface LegacyChatMapper {
    fun toModel(entity: ChatJpaEntity): Chat

    // baseTime 매핑 무시
    @Mapping(target = "baseTime", ignore = true)
    fun toEntity(model: Chat): ChatJpaEntity
}
