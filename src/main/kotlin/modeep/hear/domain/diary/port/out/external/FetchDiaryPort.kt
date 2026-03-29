package modeep.hear.domain.diary.port.out.external

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryAiComment
import java.util.UUID

interface FetchDiaryPort {
    suspend fun generateDiary(chatId: UUID) : Diary

    suspend fun addComment(diaryId: UUID) : DiaryAiComment
}