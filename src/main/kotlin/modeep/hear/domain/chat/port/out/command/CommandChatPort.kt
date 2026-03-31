package modeep.hear.domain.chat.port.out.command

import modeep.hear.domain.chat.model.Chat
import java.util.UUID

interface CommandChatPort {
    fun save(chat: Chat)

    fun delete(chatId: UUID)
}
