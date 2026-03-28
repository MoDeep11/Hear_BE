package modeep.hear.domain.chat.port.out.command

import modeep.hear.domain.chat.model.Chat

interface CommandChatPort {
    fun save(chat: Chat)
}
