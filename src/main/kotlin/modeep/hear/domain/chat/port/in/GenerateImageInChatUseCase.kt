package modeep.hear.domain.chat.port.`in`

import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.GenerateImageInChatRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.UploadImageInChatRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.GenerateImageInChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.UploadImageInChatResponse
import java.util.UUID

interface GenerateImageInChatUseCase {
    fun execute(
        chatId: UUID,
        request: GenerateImageInChatRequest
    ) : GenerateImageInChatResponse
}