package modeep.hear.domain.user.service

import modeep.hear.domain.user.model.MonthlyStatistic
import modeep.hear.domain.user.port.out.MonthlyStatisticPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SaveReportComponent(
    private val monthlyStatisticPort: MonthlyStatisticPort
) {
    @Transactional
    suspend fun save(monthlyStatistic: MonthlyStatistic) {
        monthlyStatisticPort.save(monthlyStatistic)
    }
}
