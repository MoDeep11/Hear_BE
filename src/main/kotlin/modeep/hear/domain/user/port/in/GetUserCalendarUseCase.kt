package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.response.GetUserCalendarResponse
import java.time.YearMonth

interface GetUserCalendarUseCase {
    fun execute(yearMonth: YearMonth): List<GetUserCalendarResponse>
}