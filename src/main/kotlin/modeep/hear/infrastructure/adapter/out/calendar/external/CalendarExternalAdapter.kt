package modeep.hear.infrastructure.adapter.out.calendar.external

import modeep.hear.domain.calendar.port.out.FetchExternalCalendarPort
import modeep.hear.infrastructure.external.openfeign.holiday.HolidayFeignClient
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CalendarExternalAdapter(
    private val holidayFeignClient: HolidayFeignClient,
) : FetchExternalCalendarPort {
    override fun fetch(year: Int, month: Int): Set<LocalDate> {

        val response = try {
            holidayFeignClient.getRestDays(year.toString(), "%02d".format(month))
        } catch (e: Exception) {
            TODO("error handling")
        }

        val header = response.response.header

        if (header.resultCode != "00") {
            TODO("error handling")
        }

        val holidayItems = response.response.body?.items?.item ?: emptyList()

        return holidayItems.map { it.toLocalDate() }.toSet()
    }
}