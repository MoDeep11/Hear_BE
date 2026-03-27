package modeep.hear.domain.chat.port.out.query

import modeep.hear.domain.chat.model.Message
import java.util.UUID

interface QueryMessagePort {
    fun findAllByChatId(chatId: UUID): List<Message>
}