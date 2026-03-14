package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.type.ChatStatus
import modeep.hear.domain.common.model.base.BaseTime
import java.util.UUID

data class Chat(
    val id: UUID? = null,
    val userId: UUID? = null,
    val status: ChatStatus = ChatStatus.ONGOING,
    val baseTime: BaseTime
)
