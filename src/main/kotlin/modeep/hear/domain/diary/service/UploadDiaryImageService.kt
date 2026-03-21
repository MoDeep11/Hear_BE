package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.common.port.out.S3Port
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.port.`in`.UploadDiaryImageUseCase
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.UploadDiaryImageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
        requests: List<UploadDiaryImageRequest>,
    ): List<UploadDiaryImageResponse> {
        // 검증 로직
        val user = securityPort.getCurrentUser()
        val diary = diaryPort.findById(diaryId)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
        diary.validateOwner(
            currentUserId = user.id
        )

        val diaryImages = diary.diaryImages

        requests.forEach { request ->
            when {
                // 1. 새 이미지 추가
                request.image != null && request.id == null && !request.isDeleted -> {
                    // s3 업로드
                    val imageUrl = s3Port.upload(request.image)

                    val newImage = DiaryImage.create(
                        diaryId = diary.id,
                        imageUrl = imageUrl,
                        order = request.order,
                        sourceType = DiarySourceType.MANUAL,
                        diaryImageStatus = DiaryImageStatus.COMPLETED,
                    )
                    diary.addImage(newImage)
                }

                // 2. 기존 이미지 삭제
                request.image == null && request.id != null && request.isDeleted -> {
                    val target = diaryImages.find { it.id == request.id }
                        ?: throw BusinessException(DiaryErrorCode.IMAGE_NOT_FOUND)
                    // s3 삭제
                    target.imageUrl?.let {
                        s3Url -> s3Port.delete(s3Url)
                    }
                    diary.removeImage(target)
                }

                // 3. 이미지 순서 변경
                request.image == null && request.id != null && !request.isDeleted -> {
                    val target = diaryImages.find { it.id == request.id }
                        ?: throw BusinessException(DiaryErrorCode.IMAGE_NOT_FOUND)

                    target.updateOrder(
                        order = request.order
                    )
                }

                else -> {
                    throw BusinessException(
                        DiaryErrorCode.INVALID_VALUE,
                        "잘못된 image 수정 요청입니다."
                    )
                }
            }
        }

        // 순서가 꼬엿을 경우를 대비해 인덱스 재정렬
        reorderImagesSafely(diaryImages)

        diaryPort.save(diary)
        return diary.images.map { diaryImage ->
            UploadDiaryImageResponse.toResponse(
                id = diaryImage.id,
                url = diaryImage.imageUrl,
                order = diaryImage.order,
                type = diaryImage.sourceType,
            )
        }
    }

    private fun reorderImagesSafely(diaryImages: MutableList<DiaryImage>) {
        diaryImages.sortedBy { it.order }.forEachIndexed { index, img ->
            img.updateOrder(index)
        }
    }
}

// TODO: 이벤트 발행 방식으로 transaction 밖에서 s3 처리하도록 변경