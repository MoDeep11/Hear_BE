package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.vo.ChatStatus
import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import java.util.UUID

@Aggregate
data class Chat(
    val id: UUID? = null,
    val userId: UUID? = null,
    val status: ChatStatus = ChatStatus.ONGOING,
    val baseTime: BaseTime
)
