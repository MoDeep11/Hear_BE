package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.type.MessageType
import modeep.hear.domain.chat.type.Sender
import modeep.hear.domain.common.model.base.BaseTime
import java.util.UUID

data class Message(
    val id: UUID? = null,
    val sessionId: UUID? = null,
    val sender: Sender,
    val message: String,
    val messageType: MessageType = MessageType.TEXT,
    val voiceUrl: String? = null,
    val duration: Int? = 0, // 음성 재생 시간
    val baseTime: BaseTime
)
