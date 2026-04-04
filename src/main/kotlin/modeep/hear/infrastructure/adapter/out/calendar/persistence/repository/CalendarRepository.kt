package modeep.hear.infrastructure.adapter.out.calendar.persistence.repository

import modeep.hear.infrastructure.adapter.out.calendar.persistence.entity.CalendarJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface CalendarRepository : JpaRepository<CalendarJpaEntity, LocalDate> {
    fun countByCalendarDateBetween(start: LocalDate, end: LocalDate): Long
    fun findByCalendarDateBetween(start: LocalDate, end: LocalDate): List<CalendarJpaEntity>
    fun deleteByCalendarDateBetween(start: LocalDate, end: LocalDate)
    fun findAllByCalendarDateIn(calendarDates: List<LocalDate>): List<CalendarJpaEntity>
}
