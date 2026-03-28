package modeep.hear.infrastructure.adapter.out.calendar

import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.out.CalendarPort
import modeep.hear.infrastructure.adapter.out.calendar.external.CalendarExternalAdapter
import modeep.hear.infrastructure.adapter.out.calendar.persistence.CalendarPersistenceAdapter
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CalendarCompositeAdapter(
    private val persistenceAdapter: CalendarPersistenceAdapter,
    private val externalAdapter: CalendarExternalAdapter
) : CalendarPort {
    //--Persistence--//
    override fun countByCalendarDateBetween(start: LocalDate, end: LocalDate): Long =
        persistenceAdapter.countByCalendarDateBetween(start, end)

    override fun findByCalendarDateBetween(
        start: LocalDate,
        end: LocalDate
    ): List<Calendar> =
        persistenceAdapter.findByCalendarDateBetween(start, end)

    override fun saveAll(calendars: List<Calendar>): List<Calendar> =
        persistenceAdapter.saveAll(calendars)

    override fun deleteByCalendarDateBetween(start: LocalDate, end: LocalDate) =
        persistenceAdapter.deleteByCalendarDateBetween(start, end)

    //--External--//
    override fun fetch(year: Int): Set<LocalDate> =
        externalAdapter.fetch(year)
}