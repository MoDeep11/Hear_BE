package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CallbackGenerationDiaryImageRequest

interface CallbackGenerationDiaryImageUseCase {
    suspend fun execute(
        request: CallbackGenerationDiaryImageRequest
    )
}
