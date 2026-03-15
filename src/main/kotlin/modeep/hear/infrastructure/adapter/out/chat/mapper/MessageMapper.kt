package modeep.hear.infrastructure.adapter.out.chat.mapper

import modeep.hear.domain.chat.model.Message
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.chat.entity.MessageJpaEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(
    componentModel = "spring",
    uses = [BaseTimeMapper::class]
)
interface MessageMapper {
    fun toModel(entity: MessageJpaEntity): Message

    // baseTime 매핑 무시: Spring에서 관리
    @Mapping(target = "baseTime", ignore = true)
    fun toEntity(model: Message): MessageJpaEntity
}
