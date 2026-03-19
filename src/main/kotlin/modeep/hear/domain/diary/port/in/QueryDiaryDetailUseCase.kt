package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiaryDetailResponse
import java.util.UUID

interface QueryDiaryDetailUseCase {
    fun execute(diaryId: UUID): QueryDiaryDetailResponse
}
