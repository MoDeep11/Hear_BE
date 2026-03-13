package modeep.hear.domain.diary.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.model.base.BaseTime
import modeep.hear.domain.common.model.emotion.Emotion
import modeep.hear.domain.diary.type.DiarySourceType
import java.util.UUID

@Aggregate
data class Diary(
    val id: UUID? = null,
    val userId: UUID? = null,
    val content: String,
    val emotion: Emotion,
    val tags: List<String> = emptyList(),
    val baseTime: BaseTime,
    val sourceType: DiarySourceType = DiarySourceType.AI_MADE,
    val sessionId: UUID? = null
)