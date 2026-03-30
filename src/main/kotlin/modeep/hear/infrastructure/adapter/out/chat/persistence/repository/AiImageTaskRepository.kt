package modeep.hear.infrastructure.adapter.out.chat.persistence.repository

import modeep.hear.infrastructure.adapter.out.chat.persistence.entity.AiImageTaskJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AiImageTaskRepository : JpaRepository<AiImageTaskJpaEntity, UUID> {
    fun existsByChatId(chatId: UUID): Boolean
}
