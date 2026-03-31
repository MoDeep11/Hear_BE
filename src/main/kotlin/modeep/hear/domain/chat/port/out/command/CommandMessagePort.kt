package modeep.hear.domain.chat.port.out.command

import modeep.hear.domain.chat.model.Message

interface CommandMessagePort {
    fun save(message: Message)
}
