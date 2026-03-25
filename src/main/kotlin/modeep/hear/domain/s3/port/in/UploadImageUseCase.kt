package modeep.hear.domain.s3.port.`in`

import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.UploadDiaryImageRequest

interface UploadImageUseCase {
    fun execute(
        diaryImages: MutableList<DiaryImage>? = null,
        requests: List<UploadDiaryImageRequest>
    ): List<DiaryImage>
}
