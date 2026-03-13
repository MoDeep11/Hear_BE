package modeep.hear.infrastructure.adapter.out.calendar.persistence.mapper

import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.infrastructure.adapter.out.calendar.persistence.entity.CalendarJpaEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
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
