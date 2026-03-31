package modeep.hear.infrastructure.adapter.out.user.persistence.repository

import modeep.hear.infrastructure.adapter.out.user.persistence.entity.MonthlyStatisticJpaEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.id.MonthlyStatisticId
import org.springframework.data.jpa.repository.JpaRepository

interface MonthlyStatisticRepository : JpaRepository<MonthlyStatisticJpaEntity, MonthlyStatisticId>
