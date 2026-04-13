package modeep.hear.domain.chat.model

import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import java.util.UUID

@Aggregate
data class Message(
    val id: UUID,
    val chatId: UUID,
    val sender: Sender,
    val message: String,
    val messageType: MessageType,
    val voiceUrl: String? = null,
    val baseTime: BaseTime
) {
    companion object {
        fun create(
            chatId: UUID,
            sender: Sender,
            message: String,
            messageType: MessageType,
            voiceUrl: String? = null
        ): Message {
            return Message(
                id = UUID.randomUUID(),
                chatId = chatId,
                sender = sender,
                message = message,
                messageType = messageType,
                voiceUrl = voiceUrl,
                baseTime = BaseTime()
            )
        }
    }

    fun updateMessage(message: String) =
        this.copy(message = message)
}
