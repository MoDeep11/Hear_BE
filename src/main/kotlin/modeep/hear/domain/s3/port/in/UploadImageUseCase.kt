package modeep.hear.domain.s3.port.`in`

import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.UploadDiaryImageResponse
import java.util.UUID

interface UploadImageUseCase {
    fun execute(
        images: MutableList<DiaryImage>,
        requests: List<UploadDiaryImageRequest>
    ): List<DiaryImage>
}