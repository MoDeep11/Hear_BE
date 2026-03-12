package modeep.hear.infrastructure.adapter.out.calendar.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.DayOfWeek
import java.time.LocalDate

@Entity
@Table(name = "calendar")
class CalendarJpaEntity(

    @Id
    @Column(name = "calendar_date", nullable = false, unique = true)
    val calendarDate: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    val dayOfWeek: DayOfWeek,

    @Column(name = "is_holiday", nullable = false)
    val isHoliday: Boolean = false
)