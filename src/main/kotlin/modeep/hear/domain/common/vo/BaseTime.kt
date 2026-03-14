package modeep.hear.domain.common.vo

import java.time.LocalDateTime

data class BaseTime(
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
