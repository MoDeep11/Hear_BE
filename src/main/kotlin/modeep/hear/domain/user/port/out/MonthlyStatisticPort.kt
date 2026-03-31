package modeep.hear.domain.user.port.out

import modeep.hear.domain.user.model.MonthlyStatistic
import java.util.UUID

interface MonthlyStatisticPort {
    fun findById(id: UUID): MonthlyStatistic?

    fun save(monthlyStatistic: MonthlyStatistic)
}
