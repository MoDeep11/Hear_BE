package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserStatisticsResponse
import java.time.YearMonth

interface GetUserStatisticsUseCase {
    fun execute(
        yearMonth: YearMonth
    ): UserStatisticsResponse
}