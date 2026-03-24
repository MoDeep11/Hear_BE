package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import java.util.UUID

@Aggregate
data class Chat(
    val id: UUID,
    val userId: UUID,
    val status: ChatStatus = ChatStatus.ONGOING,
    val baseTime: BaseTime
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
}
