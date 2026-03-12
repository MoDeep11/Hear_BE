package modeep.hear.infrastructure.adapter.out.calendar.persistence.repository

import modeep.hear.infrastructure.adapter.out.calendar.persistence.entity.CalendarJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface CalendarRepository : JpaRepository<CalendarJpaEntity, LocalDate>