package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.global.error.exception.BusinessException
import java.util.UUID

@Aggregate
data class Chat(
    val id: UUID,
    val userId: UUID,
    val status: ChatStatus = ChatStatus.ONGOING,
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
            throw BusinessException(DiaryErrorCode.CANNOT_ACCESS_DIARY)
        }
    }

    fun completeChat(): Chat =
        this.copy(status = ChatStatus.COMPLETED)

    fun addMessage(message: Message) {
        this.messages.add(message)
    }
}
