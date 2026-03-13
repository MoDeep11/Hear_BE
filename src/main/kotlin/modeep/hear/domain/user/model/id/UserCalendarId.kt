package modeep.hear.domain.user.model.id

import java.time.LocalDate
import java.util.UUID

data class UserCalendarId(
    val calendarDate: LocalDate,  // Calendar의 PK
    val userId: UUID? = null,
)
