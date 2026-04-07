package modeep.hear.infrastructure.adapter.out.chat.persistence.repository

import modeep.hear.infrastructure.adapter.out.chat.persistence.entity.MessageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface MessageRepository : JpaRepository<MessageJpaEntity, UUID> {
    @Query(
        """
        SELECT m FROM MessageJpaEntity m 
        WHERE m.chatId = :chatId 
        ORDER BY m.baseTime.createdAt ASC
    """
    )
    fun findAllByChatIdOrderByCreatedAt(chatId: UUID): List<MessageJpaEntity>
}
