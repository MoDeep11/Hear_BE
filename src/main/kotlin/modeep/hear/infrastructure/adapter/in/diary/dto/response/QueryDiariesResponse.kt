package modeep.hear.infrastructure.adapter.`in`.diary.dto.response

import modeep.hear.domain.diary.model.DiaryImage
import java.time.LocalDate
import java.util.UUID

data class QueryDiariesResponse(
    val id: UUID,
    val thumbnailUrl: DiaryImage,
    val tags: List<String>,
    val createdAt: LocalDate
)
