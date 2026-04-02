package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.RecommendDiaryResponse

interface RecommendDairyUseCase {
    fun execute(): RecommendDiaryResponse
}
