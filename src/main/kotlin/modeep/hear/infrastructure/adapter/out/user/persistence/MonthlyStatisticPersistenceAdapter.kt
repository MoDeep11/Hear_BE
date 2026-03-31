package modeep.hear.infrastructure.adapter.out.user.persistence

import modeep.hear.domain.user.model.MonthlyStatistic
import modeep.hear.domain.user.port.out.MonthlyStatisticPort
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.id.MonthlyStatisticId
import modeep.hear.infrastructure.adapter.out.user.persistence.mapper.MonthlyStatisticMapper
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.MonthlyStatisticRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.YearMonth
import java.util.UUID

@Component
class MonthlyStatisticPersistenceAdapter(
    private val repo: MonthlyStatisticRepository,
    private val mapper: MonthlyStatisticMapper
) : MonthlyStatisticPort {
    override fun findByUserIdAndYearMonth(userId: UUID, yearMonth: YearMonth): MonthlyStatistic? {
        return repo.findByIdOrNull(MonthlyStatisticId(userId, yearMonth))?.let { mapper.toModel(it) }
    }

    override fun save(monthlyStatistic: MonthlyStatistic) {
        repo.save(mapper.toEntity(monthlyStatistic))
    }
}
