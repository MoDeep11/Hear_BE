package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.RecommendDiaryResponse

interface RecommendDiaryUseCase {
    fun execute(): RecommendDiaryResponse
}
