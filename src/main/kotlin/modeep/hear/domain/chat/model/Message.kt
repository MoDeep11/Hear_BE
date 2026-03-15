package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import java.util.UUID

@Aggregate
data class Message(
    val id: UUID? = null,
    val sessionId: UUID,
    val sender: Sender,
    val message: String,
    val messageType: MessageType = MessageType.TEXT,
    val voiceUrl: String? = null,
    val duration: Int? = 0, // 음성 재생 시간
    val baseTime: BaseTime
)
