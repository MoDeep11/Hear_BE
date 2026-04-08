package modeep.hear.infrastructure.adapter.out.user.persistence.entity.id

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embeddable
import modeep.hear.global.converter.YearMonthConverter
import java.io.Serializable
import java.time.YearMonth
import java.util.UUID

@Embeddable
data class MonthlyStatisticIdEntity(
    @Column(name = "user_id")
    val userId: UUID,

    @Column(name = "target_year_month")
    @Convert(converter = YearMonthConverter::class)
    val targetYearMonth: YearMonth
) : Serializable
