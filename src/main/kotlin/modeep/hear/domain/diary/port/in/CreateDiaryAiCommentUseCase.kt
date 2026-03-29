package modeep.hear.domain.diary.port.`in`

import modeep.hear.domain.diary.port.dto.result.CreateDiaryAiCommentResult
import java.util.UUID

interface CreateDiaryAiCommentUseCase {
    suspend fun execute(diaryId: UUID) : CreateDiaryAiCommentResult
}