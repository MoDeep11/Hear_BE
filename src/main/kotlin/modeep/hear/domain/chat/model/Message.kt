package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import java.util.UUID

@Aggregate
data class Message(
    val id: UUID,
    val sessionId: UUID,
    val sender: Sender,
    val message: String,
    val messageType: MessageType,
    val voiceUrl: String? = null,
    val duration: Int? = null, // 음성 재생 시간
    val baseTime: BaseTime
) {
    companion object {
        fun create(
            sessionId: UUID,
            sender: Sender,
            message: String,
            messageType: MessageType,
            voiceUrl: String? = null,
            duration: Int? = null
        ): Message {
            return Message(
                id = UUID.randomUUID(),
                sessionId = sessionId,
                sender = sender,
                message = message,
                messageType = messageType,
                voiceUrl = voiceUrl,
                duration = duration,
                baseTime = BaseTime()
            )
        }
    }
}
