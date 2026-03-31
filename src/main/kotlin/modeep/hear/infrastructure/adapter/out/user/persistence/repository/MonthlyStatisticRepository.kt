package modeep.hear.infrastructure.adapter.out.user.persistence.repository

import modeep.hear.infrastructure.adapter.out.user.persistence.entity.MonthlyStatisticJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MonthlyStatisticRepository : JpaRepository<MonthlyStatisticJpaEntity, UUID>
