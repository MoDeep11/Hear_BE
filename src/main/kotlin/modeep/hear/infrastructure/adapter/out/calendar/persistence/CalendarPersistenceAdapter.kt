package modeep.hear.infrastructure.adapter.out.calendar.persistence

import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.out.CommandCalendarPort
import modeep.hear.infrastructure.adapter.out.calendar.persistence.entity.CalendarJpaEntity
import modeep.hear.infrastructure.adapter.out.calendar.persistence.mapper.CalendarMapper
import modeep.hear.infrastructure.adapter.out.calendar.persistence.repository.CalendarRepository
import org.springframework.stereotype.Component

@Component
class CalendarPersistenceAdapter(
    private val calendarRepository: CalendarRepository,
    private val calendarMapper: CalendarMapper,
): CommandCalendarPort {

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
}