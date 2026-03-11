package modeep.hear.domain.calendar.model

import modeep.hear.domain.common.annotation.Aggregate
import java.time.DayOfWeek
import java.time.LocalDate

@Aggregate
data class Calendar(
    val calendarDate: LocalDate,
    val dayOfWeek: DayOfWeek,
    val isHoliday: Boolean,
) {
    companion object {
        fun create(date: LocalDate, isHoliday: Boolean): Calendar {
            val dayOfWeek = date.dayOfWeek

            val finalIsHoliday = isHoliday ||
                    dayOfWeek == DayOfWeek.SATURDAY ||
                    dayOfWeek == DayOfWeek.SUNDAY

            return Calendar(
                calendarDate = date,
                dayOfWeek = dayOfWeek,
                isHoliday = finalIsHoliday
            )
        }
    }
}