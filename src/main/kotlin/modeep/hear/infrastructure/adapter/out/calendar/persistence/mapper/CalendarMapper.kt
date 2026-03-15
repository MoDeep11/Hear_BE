package modeep.hear.infrastructure.adapter.out.calendar.persistence.mapper

import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.infrastructure.adapter.out.calendar.persistence.entity.CalendarJpaEntity

// boolean is 접두사 문제로 직접 구현
// @Mapper(componentModel = "spring")
interface CalendarMapper {
    fun toModel(entity: CalendarJpaEntity): Calendar

    fun toEntity(model: Calendar): CalendarJpaEntity
}

// @Mapper(componentModel = "spring")
// abstract class CalendarMapper {
//
//    fun toModel(entity: CalendarJpaEntity): Calendar {
//        return Calendar(
//            calendarDate = entity.calendarDate,
//            dayOfWeek = entity.dayOfWeek,
//            isHoliday = entity.isHoliday
//        )
//    }
//
//    fun toEntity(model: Calendar): CalendarJpaEntity {
//        return CalendarJpaEntity(
//            calendarDate = model.calendarDate,
//            dayOfWeek = model.dayOfWeek,
//            isHoliday = model.isHoliday
//        )
//    }
// }
