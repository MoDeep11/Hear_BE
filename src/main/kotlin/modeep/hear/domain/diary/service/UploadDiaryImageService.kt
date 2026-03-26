package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.port.`in`.UploadDiaryImageUseCase
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.storage.port.`in`.UploadImageUseCase
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UploadDiaryImageService(
    private val diaryPort: DiaryPort,
    private val securityPort: SecurityPort,
    private val uploadImageUseCase: UploadImageUseCase
) : UploadDiaryImageUseCase {
    override fun execute(
        diaryId: UUID,
        requests: List<UploadDiaryImageRequest>
    ): List<UploadDiaryImageResponse> {
        val diary = diaryPort.findById(diaryId)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
        diary.validateOwner(securityPort.getCurrentUser().id)

        val newDiaryImages = uploadImageUseCase.execute(diary.diaryImages, requests)

        diary.updateImages(newDiaryImages)

        diaryPort.save(diary)

        return diary.images.map { diaryImage ->
            UploadDiaryImageResponse.toResponse(
                id = diaryImage.id,
                url = diaryImage.imageUrl,
                order = diaryImage.order,
                type = diaryImage.sourceType
            )
        }
    }
}
