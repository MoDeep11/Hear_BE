package modeep.hear.domain.chat.port.out.query

import modeep.hear.domain.chat.model.AiImageTask
import java.util.UUID

interface QueryAiImageTaskPort {
    fun findByChatId(chatId: UUID): AiImageTask?
}
