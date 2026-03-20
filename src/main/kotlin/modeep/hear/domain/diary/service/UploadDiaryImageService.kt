package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.common.port.out.S3Port
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.port.`in`.UploadDiaryImageUseCase
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.UpdateDiaryContentRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiariesResponse
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.UploadDiaryImageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
@Transactional
class UploadDiaryImageService(
    private val diaryPort: DiaryPort,
    private val securityPort: SecurityPort,
    private val s3Port: S3Port
) : UploadDiaryImageUseCase {
    override fun execute(
        diaryId: UUID,
        request: List<UploadDiaryImageRequest>,
    ): List<UploadDiaryImageResponse> {
        val user = securityPort.getCurrentUser()
        val diary = diaryPort.findById(diaryId)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)

        if (user.id != diary.userId) {
            throw BusinessException(DiaryErrorCode.CANNOT_DELETE_DIARY)
        }
        if (images.isEmpty() || images.all { it.isEmpty }) {
            throw BusinessException(DiaryErrorCode.IMAGE_REQUIRED)
        }

        val imageUrls: List<String> =
            images.map{ s3Port.upload(it) }

        val diaryImages = imageUrls.mapIndexed { index, url ->
            DiaryImage.create(
                diaryId = diary.id,
                imageUrl = url,
                order = index,
                sourceType = DiarySourceType.MANUAL,
                diaryImageStatus = DiaryImageStatus.COMPLETED,  // 사용자 추가 이미지는 complete
            )
        }

        return diaryImages.map { diary ->
            UploadDiaryImageResponse.toResponse(
                id = diary.id,
                url = diary.imageUrl,
                order = diary.order,
                type = diary.sourceType,
            )
        }
    }
}
