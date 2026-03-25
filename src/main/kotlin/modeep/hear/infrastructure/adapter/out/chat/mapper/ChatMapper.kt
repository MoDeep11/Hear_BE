package modeep.hear.infrastructure.adapter.out.chat.mapper

import modeep.hear.domain.chat.model.Chat
import modeep.hear.infrastructure.adapter.out.chat.entity.ChatJpaEntity

interface ChatMapper {
    fun toModel(entity: ChatJpaEntity): Chat

    fun toEntity(model: Chat): ChatJpaEntity
}
