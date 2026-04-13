package modeep.hear.infrastructure.adapter.out.user.event.event

import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

data class CreateUserCalendarEvent(
    val userId: UUID,
    val dates: List<LocalDate>,
    val yearMonth: YearMonth
)
