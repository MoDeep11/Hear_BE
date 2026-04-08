package modeep.hear.domain.storage.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.common.event.EventPublisher
import modeep.hear.domain.diary.event.DiaryImageDeletedEvent
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.domain.storage.exception.StorageErrorCode
import modeep.hear.domain.storage.port.`in`.UploadImageUseCase
import modeep.hear.domain.storage.port.out.PendingUploadPort
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.domain.storage.vo.FileData
import modeep.hear.domain.storage.vo.ImageAction
import modeep.hear.domain.storage.vo.ServiceType
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UploadImageService(
    private val eventPublisher: EventPublisher,
    private val pendingUploadPort: PendingUploadPort,
    private val storagePort: StoragePort,
    private val securityPort: SecurityPort
) : UploadImageUseCase {
    override fun execute(
        diaryImages: MutableList<DiaryImage>?,
        requests: List<UploadDiaryImageRequest>,
        images: List<MultipartFile>?,
        serviceType: ServiceType
    ): List<DiaryImage> {
        val sortedRequests = renameDuplicateFileNames(requests.sortedByDescending { it.action == ImageAction.DELETE })
        val existedImages = diaryImages ?: mutableListOf()
        val urlsToDelete = mutableSetOf<String>()

        val user = securityPort.getCurrentUser()

        val remainingImages = images.orEmpty().toMutableList()
        val imagesToSave = mutableListOf<Pair<MultipartFile, FileData>>()

        sortedRequests.forEach { request ->
            when (request.action) {
                // 1. 새 이미지 추가
                ImageAction.ADD -> {
                    val imageIndex = remainingImages.indexOfFirst { it.originalFilename == request.fileName }
                    if (imageIndex == -1) throw BusinessException(StorageErrorCode.INVALID_FILE)
                    val image = remainingImages.removeAt(imageIndex)
                    val fileData = FileData.create(
                        image,
                        serviceType,
                        user.id
                    )

                    val url = storagePort.getUrlToUpload(fileData)
                    val newImage = DiaryImage.create(
                        imageUrl = url,
                        order = request.order,
                        sourceType = DiarySourceType.MANUAL,
                        diaryImageStatus = DiaryImageStatus.SUCCESS
                    )
                    existedImages.add(newImage)
                    imagesToSave.add(Pair(image, fileData))

                    val s3Key = storagePort.extractKey(url)
                    pendingUploadPort.deleteByS3Key(s3Key)
                }

                // 2. 기존 이미지 삭제
                ImageAction.DELETE -> {
                    val target = existedImages.find { it.id == request.id }
                        ?: throw BusinessException(DiaryErrorCode.IMAGE_NOT_FOUND)
                    target.imageUrl?.let { urlsToDelete.add(it) }
                    existedImages.remove(target)
                }

                // 3. 이미지 순서 변경
                ImageAction.UPDATE_ORDER -> {
                    val index = existedImages.indexOfFirst { it.id == request.id }
                    if (index == -1) throw BusinessException(DiaryErrorCode.IMAGE_NOT_FOUND)

                    // 새로운 순서가 적용된 복사본으로 교체
                    existedImages[index] = existedImages[index].updateOrder(order = request.order)
                }
            }
        }

        // 순서가 꼬엿을 경우를 대비해 인덱스 재정렬
        reorderImagesSafely(existedImages)

        if (urlsToDelete.isNotEmpty()) {
            eventPublisher.publish(DiaryImageDeletedEvent(urlsToDelete.toList()))
        }

        if (imagesToSave.isNotEmpty()) {
            imagesToSave.forEach { (image, fileData) ->
                storagePort.upload(image, fileData)
            }
        }

        return existedImages
    }

    override fun executeInChat(
        requests: List<UploadDiaryImageRequest>
    ): List<DiaryImage> {
        val sortedRequests = requests.sortedByDescending { it.action == ImageAction.DELETE }
        val images = mutableListOf<DiaryImage>()

        sortedRequests.forEach { request ->
            when (request.action) {
                // 1. 새 이미지 추가
                ImageAction.ADD -> {
                    val newImage = DiaryImage.create(
                        imageUrl = request.imageUrl,
                        order = request.order,
                        sourceType = DiarySourceType.MANUAL,
                        diaryImageStatus = DiaryImageStatus.SUCCESS
                    )
                    images.add(newImage)

                    val s3Key = storagePort.extractKey(request.imageUrl!!)
                    pendingUploadPort.deleteByS3Key(s3Key)
                }
                else -> throw BusinessException(DiaryErrorCode.IMAGE_NOT_FOUND)
            }
        }

        reorderImagesSafely(images)
        return images
    }

    private fun reorderImagesSafely(diaryImages: MutableList<DiaryImage>) {
        val reordered = diaryImages
            .sortedBy { it.order }
            .mapIndexed { index, img -> img.updateOrder(index) }
        diaryImages.clear()
        diaryImages.addAll(reordered)
    }

    private fun renameDuplicateFileNames(requests: List<UploadDiaryImageRequest>): List<UploadDiaryImageRequest> {
        val nameCountMap = mutableMapOf<String, Int>()

        return requests.map { request ->
            val originalName = request.fileName

            // fileName이 null인 경우는 그대로 반환
            if (originalName == null) {
                request
            } else {
                // 해당 이름이 몇 번째인지 확인
                val count = nameCountMap.getOrDefault(originalName, 0)

                val newName = if (count == 0) {
                    originalName // 처음 등장하면 그대로 사용
                } else {
                    // 확장자가 있는 경우와 없는 경우를 구분하여 숫자 추가
                    val extensionIndex = originalName.lastIndexOf('.')
                    if (extensionIndex != -1) {
                        val name = originalName.substring(0, extensionIndex)
                        val ext = originalName.substring(extensionIndex)
                        "$name($count)$ext"
                    } else {
                        "$originalName($count)"
                    }
                }

                // 카운트 증가 및 객체 복사 (data class의 copy 활용)
                nameCountMap[originalName] = count + 1
                request.copy(fileName = newName)
            }
        }
    }
}
