package modeep.hear.domain.s3.service

import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.domain.s3.port.`in`.UploadImageUseCase
import modeep.hear.domain.s3.port.out.S3Port
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.UploadDiaryImageRequest
import org.springframework.stereotype.Service

@Service
class UploadImageService(
    private val s3Port: S3Port
) : UploadImageUseCase {
    override fun execute(
        diaryImages: MutableList<DiaryImage>?,
        requests: List<UploadDiaryImageRequest>
    ): List<DiaryImage> {
        val images = diaryImages ?: mutableListOf()
        val urlsToDelete = mutableSetOf<String>()

        requests.forEach { request ->
            when {
                // 1. 새 이미지 추가
                !request.imageUrl.isNullOrBlank() && request.id == null && !request.isDeleted -> {
                    val newImage = DiaryImage.create(
                        imageUrl = request.imageUrl,
                        order = request.order,
                        sourceType = DiarySourceType.MANUAL,
                        diaryImageStatus = DiaryImageStatus.COMPLETED
                    )
                    images.add(newImage)
                }

                // 2. 기존 이미지 삭제
                request.imageUrl.isNullOrBlank() && request.id != null && request.isDeleted -> {
                    val target = images.find { it.id == request.id }
                        ?: throw BusinessException(DiaryErrorCode.IMAGE_NOT_FOUND)
                    target.imageUrl?.let { urlsToDelete.add(it) }
                    images.remove(target)
                }

                // 3. 이미지 순서 변경
                request.imageUrl.isNullOrBlank() && request.id != null && !request.isDeleted -> {
                    val target = images.find { it.id == request.id }
                        ?: throw BusinessException(DiaryErrorCode.IMAGE_NOT_FOUND)

                    target.updateOrder(order = request.order)
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
        reorderImagesSafely(images)

        urlsToDelete.forEach { url ->
            s3Port.delete(url)
        }

        return images
    }

    private fun reorderImagesSafely(diaryImages: MutableList<DiaryImage>) {
        val reordered = diaryImages
            .sortedBy { it.order }
            .mapIndexed { index, img -> img.updateOrder(index) }
        diaryImages.clear()
        diaryImages.addAll(reordered)
    }
}
