package modeep.hear.domain.chat.port.out.query

import java.util.UUID

interface QueryAiImageTaskPort {
    fun existsByChatId(chatId: UUID): Boolean
}