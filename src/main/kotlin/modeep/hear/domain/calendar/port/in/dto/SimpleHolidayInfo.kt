package modeep.hear.domain.calendar.port.`in`.dto

import java.time.LocalDate

data class SimpleHolidayInfo(
    val date: LocalDate,
    val name: String
)