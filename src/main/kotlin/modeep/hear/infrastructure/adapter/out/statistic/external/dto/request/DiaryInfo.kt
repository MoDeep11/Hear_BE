package modeep.hear.infrastructure.adapter.out.statistic.external.dto.request

import modeep.hear.domain.diary.model.Diary
import java.util.UUID

data class DiaryInfo(
    val diaryId: UUID,
    val date: String,
    val content: String,
    val emotion: String
) {
    companion object {
        fun from(diary: Diary): DiaryInfo = DiaryInfo(
            diaryId = diary.id,
            date = diary.baseTime.createdAt.toLocalDate().toString(),
            content = diary.content,
            emotion = diary.emotion.name
        )
    }
}
