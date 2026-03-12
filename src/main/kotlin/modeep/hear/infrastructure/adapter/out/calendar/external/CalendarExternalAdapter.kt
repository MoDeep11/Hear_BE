package modeep.hear.infrastructure.adapter.out.calendar.external

import modeep.hear.domain.calendar.port.out.FetchExternalCalendarPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
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
            throw BusinessException(GlobalErrorCode.EXTERNAL_API_ERROR, e.message)
        }

        val header = response.response.header

        if (header.resultCode != "00") {
            throw BusinessException(GlobalErrorCode.EXTERNAL_API_ERROR, header.resultMsg)
        }

        val holidayItems = response.response.body?.items?.item ?: emptyList()

        return holidayItems.map { it.toLocalDate() }.toSet()
    }
}