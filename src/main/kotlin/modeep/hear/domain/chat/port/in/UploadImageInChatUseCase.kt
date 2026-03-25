package modeep.hear.domain.chat.port.`in`

import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.UploadDiaryImageResponse
import java.util.UUID

interface UploadImageInChatUseCase {
    fun execute(
        chatId: UUID,
        request: List<UploadDiaryImageRequest>
    ) : UploadDiaryImageResponse
}