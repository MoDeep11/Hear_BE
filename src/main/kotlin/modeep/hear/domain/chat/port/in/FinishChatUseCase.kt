package modeep.hear.domain.chat.port.`in`

import java.util.UUID

interface FinishChatUseCase {
    suspend fun execute(chatId: UUID)
}
