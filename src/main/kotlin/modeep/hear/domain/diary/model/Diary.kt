package modeep.hear.domain.diary.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.diary.vo.DiarySourceType
import java.util.UUID

@Aggregate
data class Diary(
    val id: UUID? = null,
    val userId: UUID? = null,
    val content: String,
    val emotion: Emotion,
    val tags: List<String>? = null,
    val baseTime: BaseTime,
    val sourceType: DiarySourceType = DiarySourceType.AI_MADE,
    val sessionId: UUID? = null,
    val diaryImages: List<DiaryImage> = emptyList()
) {
    companion object {
        fun create(
            userId: UUID,
            content: String,
            emotion: Emotion,
            tags: List<String>? = null,
            sessionId: UUID? = null
        ): Diary {
            return Diary(
                id = UUID.randomUUID(),
                userId = userId,
                content = content,
                emotion = emotion,
                tags = tags,
                baseTime = BaseTime(),
                sessionId = sessionId
            )
        }
    }
}
