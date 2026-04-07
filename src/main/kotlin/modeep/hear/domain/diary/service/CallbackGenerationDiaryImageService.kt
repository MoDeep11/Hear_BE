package modeep.hear.domain.diary.service

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.chat.port.out.AiImageTaskPort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.port.`in`.CallbackGenerationDiaryImageUseCase
import modeep.hear.domain.diary.port.out.DiaryImagePort
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CallbackGenerationDiaryImageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

private val log = KotlinLogging.logger {}

@Service
@Transactional
class CallbackGenerationDiaryImageService(
    private val diaryImagePort: DiaryImagePort,
    private val taskPort: AiImageTaskPort
) : CallbackGenerationDiaryImageUseCase {
    override suspend fun execute(
        request: CallbackGenerationDiaryImageRequest
    ) {
        checkStatus(request.status)

        val image = DiaryImage.create(
            diaryId = request.diaryId,
            imageUrl = request.imageUrl,
            order = 10,
            sourceType = DiarySourceType.AI_MADE,
            diaryImageStatus = DiaryImageStatus.SUCCESS
        )

        diaryImagePort.save(image)
        completeTask(request.diaryId)
    }

    private fun checkStatus(status: DiaryImageStatus) {
        if (status != DiaryImageStatus.SUCCESS) {
            throw BusinessException(DiaryErrorCode.DIARY_IMAGE_GENERATION_FAILED)
        }
    }

    private suspend fun completeTask(diaryId: UUID) {
        val task = taskPort.findByDiaryId(diaryId)
            ?: run {
                log.info { "Skip duplicate image callback: diaryId=[$diaryId]" }
                return
            }
        taskPort.delete(task)
    }
}
