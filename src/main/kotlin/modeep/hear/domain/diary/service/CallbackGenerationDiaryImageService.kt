package modeep.hear.domain.diary.service

import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.port.`in`.CallbackGenerationDiaryImageUseCase
import modeep.hear.domain.diary.port.out.DiaryImagePort
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CallbackGenerationDiaryImageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class CallbackGenerationDiaryImageService(
    private val checkUserWithDiaryService: CheckUserWithDiaryService,
    private val diaryImagePort: DiaryImagePort
) : CallbackGenerationDiaryImageUseCase {
    override suspend fun execute(
        diaryId: UUID,
        request: CallbackGenerationDiaryImageRequest
    ) {
        checkStatus(request.status)
        checkUserWithDiaryService.executeWithSuspend(diaryId)

        val image = DiaryImage.create(
            diaryId = diaryId,
            imageUrl = request.imageUrl,
            order = 10,
            sourceType = DiarySourceType.AI_MADE,
            diaryImageStatus = DiaryImageStatus.SUCCESS
        )

        diaryImagePort.save(image)
    }

    private suspend fun checkStatus(status: DiaryImageStatus) {
        if (status != DiaryImageStatus.SUCCESS) {
            throw Exception("Failed to generate diary image")
        }
    }
}
