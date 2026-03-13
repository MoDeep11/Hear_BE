package modeep.hear.domain.common.model.base

import java.time.LocalDateTime

data class BaseTime(
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
