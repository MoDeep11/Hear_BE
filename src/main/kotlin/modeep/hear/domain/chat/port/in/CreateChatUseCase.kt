package modeep.hear.domain.chat.port.`in`

import modeep.hear.domain.user.model.User
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse

interface CreateChatUseCase {
    suspend fun execute(user: User): CreateChatResponse
}
