package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.UploadDiaryImageResponse
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface UploadDiaryImageUseCase {
    fun execute(
        diaryId: UUID,
        requests: List<UploadDiaryImageRequest>,
    ): List<UploadDiaryImageResponse>
}
