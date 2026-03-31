package modeep.hear.domain.chat.port.out.query

import modeep.hear.domain.chat.model.Chat
import java.util.UUID

interface QueryChatPort {
    fun findById(chatId: UUID): Chat?

    fun existsById(chatId: UUID): Boolean
}
