package modeep.hear.infrastructure.adapter.out.chat.persistence.repository

import modeep.hear.infrastructure.adapter.out.chat.entity.ChatJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ChatRepository : JpaRepository<ChatJpaEntity, UUID>
