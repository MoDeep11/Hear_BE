package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CreateDiaryRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.CreateDiaryResponse

interface CreateDiaryUseCase {
    fun execute(
        request: CreateDiaryRequest
    ): CreateDiaryResponse
}
