package modeep.hear.domain.chat.port.`in`

import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.UploadImageInChatMetaRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface UploadImageInChatUseCase {
    fun execute(
        chatId: UUID,
        files: List<MultipartFile>,
        requests: List<UploadImageInChatMetaRequest>
    ): List<UploadDiaryImageResponse>
}
