package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CallbackGenerationDiaryImageRequest
import java.util.UUID

interface CallbackGenerationDiaryImageUseCase {
    suspend fun execute(
        diaryId: UUID,
        request: CallbackGenerationDiaryImageRequest
    )
}
