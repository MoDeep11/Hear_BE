package modeep.hear.infrastructure.adapter.out.chat.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.global.common.entity.BaseEntity
import java.util.UUID

@Entity
@Table(name = "chats")
class ChatJpaEntity(
    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    val status: ChatStatus = ChatStatus.ONGOING,

    id: UUID
) : BaseEntity(id)
