package modeep.hear.domain.chat.service

import modeep.hear.domain.chat.port.`in`.UploadImageInChatUseCase
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.port.out.DiaryImagePort
import modeep.hear.domain.storage.port.`in`.UploadImageUseCase
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UploadImageInChatService(
    private val diaryImagePort: DiaryImagePort,
    private val uploadImageUseCase: UploadImageUseCase,
    private val checkUserWithChatService: CheckUserWithChatService
) : UploadImageInChatUseCase {
    override fun execute(
        chatId: UUID,
        request: List<UploadDiaryImageRequest>
    ): List<UploadDiaryImageResponse> {
        checkUserWithChatService.execute(chatId)
        val images = uploadImageUseCase.executeInChat(
            requests = request
        )

        val diaryImages = images.map { image ->
            DiaryImage.create(
                imageUrl = image.imageUrl,
                order = image.order,
                sourceType = image.sourceType,
                diaryImageStatus = image.diaryImageStatus,
                chatId = chatId
            )
        }

        diaryImagePort.saveAll(diaryImages)

        return diaryImages.map { img ->
            UploadDiaryImageResponse.toResponse(
                id = img.id,
                url = img.imageUrl,
                order = img.order,
                type = img.sourceType
            )
        }
    }
}
