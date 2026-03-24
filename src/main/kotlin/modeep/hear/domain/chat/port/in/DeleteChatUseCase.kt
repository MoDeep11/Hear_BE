package modeep.hear.domain.chat.port.`in`

import java.util.UUID

interface DeleteChatUseCase {
    fun execute(chatId: UUID)
}