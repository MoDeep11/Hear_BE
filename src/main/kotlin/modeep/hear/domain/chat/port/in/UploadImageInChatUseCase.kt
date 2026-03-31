package modeep.hear.domain.chat.port.`in`

import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import java.util.UUID

interface UploadImageInChatUseCase {
    fun execute(
        chatId: UUID,
        request: List<UploadDiaryImageRequest>
    ): List<UploadDiaryImageResponse>
}
