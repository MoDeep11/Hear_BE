package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import java.util.UUID

interface UploadDiaryImageUseCase {
    fun execute(
        diaryId: UUID,
        requests: List<UploadDiaryImageRequest>
    ): List<UploadDiaryImageResponse>
}
