package modeep.hear.infrastructure.adapter.`in`.diary.dto.response

import java.time.LocalDate
import java.util.UUID

data class QueryDiariesResponse(
    val id: UUID,
    val thumbnailUrl: String,
    val tags: List<String>,
    val createdAt: LocalDate
)
