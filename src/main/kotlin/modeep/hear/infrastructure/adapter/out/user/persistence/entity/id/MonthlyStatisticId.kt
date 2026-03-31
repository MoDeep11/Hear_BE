package modeep.hear.infrastructure.adapter.out.user.persistence.entity.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.time.YearMonth
import java.util.UUID

@Embeddable
data class MonthlyStatisticId(
    @Column(name = "user_id")
    val userId: UUID,

    @Column(name = "target_year_month")
    val targetYearMonth: YearMonth
) : Serializable