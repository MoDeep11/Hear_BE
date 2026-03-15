package modeep.hear.infrastructure.adapter.out.calendar.persistence.mapper

import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.infrastructure.adapter.out.calendar.persistence.entity.CalendarJpaEntity

class CalendarMapperImpl() : CalendarMapper {
    override fun toModel(entity: CalendarJpaEntity): Calendar {
        return Calendar(
            calendarDate = entity.calendarDate,
            dayOfWeek = entity.dayOfWeek,
            isHoliday = entity.isHoliday
        )
    }

    override fun toEntity(model: Calendar): CalendarJpaEntity {
        return CalendarJpaEntity(
            calendarDate = model.calendarDate,
            dayOfWeek = model.dayOfWeek,
            isHoliday = model.isHoliday
        )
    }
}
