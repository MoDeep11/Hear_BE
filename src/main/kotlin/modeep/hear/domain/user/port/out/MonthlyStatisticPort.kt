package modeep.hear.domain.user.port.out

import modeep.hear.domain.user.model.MonthlyStatistic
import java.time.YearMonth
import java.util.UUID

interface MonthlyStatisticPort {
    fun findByUserIdAndYearMonth(userId: UUID, yearMonth: YearMonth): MonthlyStatistic

    fun save(monthlyStatistic: MonthlyStatistic)
}
