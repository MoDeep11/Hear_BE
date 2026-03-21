package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.UpdateDiaryContentRequest
import java.util.UUID

interface UpdateDiaryContentUseCase {
    fun execute(
        diaryId: UUID,
        request: UpdateDiaryContentRequest
    )
}
