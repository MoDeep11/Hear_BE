package modeep.hear.infrastructure.adapter.out.chat.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
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

    @OneToMany(mappedBy = "chat", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("createdAt DESC")  // 최신순 정렬
    val messages: MutableList<MessageJpaEntity> = mutableListOf(),

    id: UUID
) : BaseEntity(id) {

    fun addMessage(message: MessageJpaEntity) {
        this.messages.add(message)
        if (message.session != this) {
            message.assignChat(this)
        }
    }

    fun updateMessages(newMessages: List<MessageJpaEntity>) {
        this.messages.clear()
        newMessages.forEach { this.addMessage(it) }
    }
}
