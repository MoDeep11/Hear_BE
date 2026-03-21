package modeep.hear.infrastructure.adapter.`in`.diary.dto.response

import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.diary.vo.DiarySourceType
import java.time.LocalDateTime
import java.util.UUID

data class CreateDiaryResponse(
    val id: UUID,
    val content: String,
    val emotion: Emotion,
    val tags: List<String>,
    val sources: DiarySourceType,
    val createdAt: LocalDateTime
)
