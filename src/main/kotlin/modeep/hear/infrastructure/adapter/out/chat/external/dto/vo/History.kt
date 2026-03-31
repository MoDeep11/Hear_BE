package modeep.hear.infrastructure.adapter.out.chat.external.dto.vo

import modeep.hear.domain.chat.model.Message

data class History(
    val role: String,
    val content: String
) {
    companion object {
        fun from(message: Message) = History(
            role = message.sender.name,
            content = message.message
        )
    }
}
