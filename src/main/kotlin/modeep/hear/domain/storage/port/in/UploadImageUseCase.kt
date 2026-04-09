package modeep.hear.domain.storage.port.`in`

import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.storage.vo.ServiceType
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.UploadImageInChatMetaRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import org.springframework.web.multipart.MultipartFile

interface UploadImageUseCase {
    fun execute(
        diaryImages: MutableList<DiaryImage>?,
        requests: List<UploadDiaryImageRequest>,
        images: List<MultipartFile>?,
        serviceType: ServiceType
    ): List<DiaryImage>

    fun executeInChat(
        images: List<MultipartFile>,
        requests: List<UploadImageInChatMetaRequest>
    ): List<DiaryImage>
}
