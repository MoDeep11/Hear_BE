package modeep.hear.domain.diary.port.`in`

import java.util.UUID

interface CreateDiaryAiCommentUseCase {
    suspend fun execute(diaryId: UUID)
}
