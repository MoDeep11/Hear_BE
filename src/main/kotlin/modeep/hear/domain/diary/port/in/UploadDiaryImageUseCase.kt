package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.UploadDiaryImageResponse
import org.springframework.web.multipart.MultipartFile

interface UploadDiaryImageUseCase {
    fun execute(
        images: List<MultipartFile>
    ): UploadDiaryImageResponse
}
