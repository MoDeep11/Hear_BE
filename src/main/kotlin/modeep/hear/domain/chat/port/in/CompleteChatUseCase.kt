package modeep.hear.domain.chat.port.`in`

import java.util.UUID

interface CompleteChatUseCase {
    fun execute(chatId: UUID)
}
