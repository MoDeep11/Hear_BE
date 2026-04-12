package modeep.hear.infrastructure.adapter.`in`.diary.dto.response

import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.diary.vo.DiarySourceType
import java.time.LocalDate
import java.util.UUID

data class QueryDiaryDetailResponse(
    val id: UUID,
    val userId: UUID,
    val content: String,
    val emotion: Emotion,
    val imageUrls: List<String>?,
    val aiComment: String?,
    val tags: List<String>?,
    val sourceType: DiarySourceType,
    val chatId: UUID? = null,
    val createdAt: LocalDate,
    val updatedAt: LocalDate
)
