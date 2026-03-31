package modeep.hear.infrastructure.adapter.out.chat.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.global.common.entity.BaseEntity
import java.util.UUID

@Entity
@Table(name = "messages")
class MessageJpaEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    var chat: ChatJpaEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "sender", nullable = false, length = 8)
    val sender: Sender,

    @Column(name = "message", nullable = false, length = 1000)
    val message: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false, length = 8)
    val messageType: MessageType = MessageType.TEXT,

    @Column(name = "voice_url", length = 512)
    val voiceUrl: String? = null,

    @Column(name = "duration")
    val duration: Long? = null, // milliseconds

    id: UUID
) : BaseEntity(id) {

    fun assignChat(chat: ChatJpaEntity) {
        this.chat.messages.remove(this)
        this.chat = chat
        if (!chat.messages.contains(this)) {
            chat.addMessage(this)
        }
    }
}
