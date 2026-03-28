package modeep.hear.infrastructure.adapter.out.chat.persistence.repository

import modeep.hear.infrastructure.adapter.out.chat.persistence.entity.MessageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MessageRepository : JpaRepository<MessageJpaEntity, UUID> {
    fun findAllByChatId(chatId: UUID): MutableList<MessageJpaEntity>
}
