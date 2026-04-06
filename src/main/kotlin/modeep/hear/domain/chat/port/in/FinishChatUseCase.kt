package modeep.hear.domain.chat.port.`in`

import modeep.hear.domain.user.model.User
import java.util.UUID

interface FinishChatUseCase {
    suspend fun execute(
        chatId: UUID,
        user: User
    )
}
