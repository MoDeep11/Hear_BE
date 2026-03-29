package modeep.hear.domain.diary.port.`in`

import modeep.hear.domain.diary.model.Diary

interface CreateDiaryAiCommentUseCase {
    suspend fun execute(diary: Diary)
}
