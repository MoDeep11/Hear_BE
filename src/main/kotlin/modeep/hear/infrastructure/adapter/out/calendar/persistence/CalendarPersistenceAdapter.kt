package modeep.hear.infrastructure.adapter.out.calendar.persistence

import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.out.CommandCalendarPort
import modeep.hear.domain.calendar.port.out.QueryCalendarPort
import modeep.hear.infrastructure.adapter.out.calendar.persistence.entity.CalendarJpaEntity
import modeep.hear.infrastructure.adapter.out.calendar.persistence.mapper.CalendarMapper
import modeep.hear.infrastructure.adapter.out.calendar.persistence.repository.CalendarRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CalendarPersistenceAdapter(
    private val calendarRepository: CalendarRepository,
    private val calendarMapper: CalendarMapper,
): CommandCalendarPort, QueryCalendarPort {

    //--Query--//
    override fun countByCalendarDateBetween(start: LocalDate, end: LocalDate): Long {
        return calendarRepository.countByCalendarDateBetween(start, end)
    }

    override fun findByCalendarDateBetween(start: LocalDate, end: LocalDate): List<Calendar> {
        val calendarJpaEntities = calendarRepository.findByCalendarDateBetween(start, end)
        return calendarJpaEntities.map { calendarMapper.toModel(it) }
    }

    //--Command--//
    override fun saveAll(calendars: List<Calendar>): List<Calendar> {
        val savedCalendar = calendarRepository.saveAll(calendars.map { model ->
            CalendarJpaEntity(
                calendarDate = model.calendarDate,
                dayOfWeek = model.dayOfWeek,
                isHoliday = model.isHoliday
            )
        })

        return savedCalendar.map { calendarMapper.toModel(it) }
    }

    override fun deleteByCalendarDateBetween(start: LocalDate, end: LocalDate) {
        calendarRepository.deleteByCalendarDateBetween(start, end)
    }
}