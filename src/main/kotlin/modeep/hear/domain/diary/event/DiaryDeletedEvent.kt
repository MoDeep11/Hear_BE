package modeep.hear.domain.diary.event

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class DiaryDeletedEvent(
    val now: LocalDateTime,
    val userId: UUID,
    val createdAtOfDiary: LocalDateTime,
    val hasTodayDiary: Boolean,
    val totalCount: Int,
    val recentDates: List<LocalDate>
)
