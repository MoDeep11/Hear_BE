package modeep.hear.domain.storage.service

import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.domain.storage.port.`in`.UploadImageUseCase
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.domain.storage.vo.ImageAction
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import org.springframework.stereotype.Service

@Service
class UploadImageService(
    private val storagePort: StoragePort
) : UploadImageUseCase {
    override fun execute(
        diaryImages: MutableList<DiaryImage>?,
        requests: List<UploadDiaryImageRequest>
    ): List<DiaryImage> {
        val sortedRequests = requests.sortedByDescending { it.action == ImageAction.DELETE }
        val images = diaryImages ?: mutableListOf()
        val urlsToDelete = mutableSetOf<String>()

        sortedRequests.forEach { request ->
            when (request.action) {
                // 1. 새 이미지 추가
                ImageAction.ADD -> {
                    val newImage = DiaryImage.create(
                        imageUrl = request.imageUrl,
                        order = request.order,
                        sourceType = DiarySourceType.MANUAL,
                        diaryImageStatus = DiaryImageStatus.COMPLETED
                    )
                    images.add(newImage)
                }

                // 2. 기존 이미지 삭제
                ImageAction.DELETE -> {
                    val target = images.find { it.id == request.id }
                        ?: throw BusinessException(DiaryErrorCode.IMAGE_NOT_FOUND)
                    target.imageUrl?.let { urlsToDelete.add(it) }
                    images.remove(target)
                }

                // 3. 이미지 순서 변경
                ImageAction.UPDATE_ORDER -> {
                    val target = images.find { it.id == request.id }
                        ?: throw BusinessException(DiaryErrorCode.IMAGE_NOT_FOUND)

                    target.updateOrder(order = request.order)
                }
            }
        }

        // 순서가 꼬엿을 경우를 대비해 인덱스 재정렬
        reorderImagesSafely(images)

        urlsToDelete.forEach { url ->
            storagePort.delete(url)
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
