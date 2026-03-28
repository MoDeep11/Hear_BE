package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.global.error.exception.BusinessException
import java.util.UUID

@Aggregate
data class Chat(
    val id: UUID,
    val userId: UUID,
    val status: ChatStatus = ChatStatus.CONTINUE,
    val baseTime: BaseTime,
    val messages: MutableList<Message> = mutableListOf()
) {
    companion object {
        fun create(userId: UUID): Chat {
            return Chat(
                id = UUID.randomUUID(),
                userId = userId,
                baseTime = BaseTime()
            )
        }
    }

    fun validateOwner(currentUserId: UUID) {
        if (this.userId != currentUserId) {
            throw BusinessException(ChatErrorCode.CANNOT_ACCESS_CHAT)
        }
    }

    fun completeChat(): Chat =
        this.copy(status = ChatStatus.FINISH)

    fun addMessage(message: Message) {
        this.messages.add(message)
    }
}
