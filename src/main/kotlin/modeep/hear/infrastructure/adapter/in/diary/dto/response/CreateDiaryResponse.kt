package modeep.hear.infrastructure.adapter.`in`.diary.dto.response

import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.vo.DiarySourceType
import java.time.LocalDateTime
import java.util.UUID

data class CreateDiaryResponse(
    val id: UUID,
    val content: String,
    val emotion: Emotion,
    val imageUrls: List<String>,
    val tags: List<String>,
    val sources: DiarySourceType,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(diary: Diary) = CreateDiaryResponse(
            id = diary.id,
            content = diary.content,
            emotion = diary.emotion,
            imageUrls = diary.diaryImages.mapNotNull { it.imageUrl },
            tags = diary.tags,
            sources = diary.sourceType,
            createdAt = diary.baseTime.createdAt
        )
    }
}
