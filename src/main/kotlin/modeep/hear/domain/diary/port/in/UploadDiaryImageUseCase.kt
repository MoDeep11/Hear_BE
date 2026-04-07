package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface UploadDiaryImageUseCase {
    fun execute(
        diaryId: UUID,
        requests: List<UploadDiaryImageRequest>,
        images: List<MultipartFile>?
    ): List<UploadDiaryImageResponse>
}
