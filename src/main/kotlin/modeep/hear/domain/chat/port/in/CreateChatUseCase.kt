package modeep.hear.domain.chat.port.`in`

import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse

interface CreateChatUseCase {
    suspend fun execute(): CreateChatResponse
}
