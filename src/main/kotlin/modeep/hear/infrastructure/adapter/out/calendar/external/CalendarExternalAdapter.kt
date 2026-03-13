package modeep.hear.infrastructure.adapter.out.calendar.external

import feign.FeignException
import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.calendar.port.out.FetchExternalCalendarPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.external.openfeign.holiday.HolidayFeignClient
import org.springframework.stereotype.Component
import java.time.LocalDate

private val log = KotlinLogging.logger {}

@Component
class CalendarExternalAdapter(
    private val holidayFeignClient: HolidayFeignClient
) : FetchExternalCalendarPort {
    override fun fetch(year: Int, month: Int): Set<LocalDate> {
        val response = try {
            holidayFeignClient.getRestDays(year.toString(), "%02d".format(month))
        } catch (e: Exception) {
            val rawBody = if (e is FeignException) e.contentUTF8() else null
            val defaultMsg = "응답 본문을 추출할 수 없음 (Cause: ${e.cause})"
            log.error { "파싱 중 진짜 에러 발생: ${rawBody ?: defaultMsg}" }

            throw BusinessException(
                errorCode = GlobalErrorCode.EXTERNAL_API_ERROR,
                message = "공공데이터 API 응답 파싱 실패: ${e.message}",
                details = rawBody ?: defaultMsg
            )
        }

        val header = response.response.header

        if (header.resultCode != "00") {
            throw BusinessException(GlobalErrorCode.EXTERNAL_API_ERROR, header.resultMsg)
        }

        val holidayItems = response.response.body?.items?.item ?: emptyList()

        return holidayItems.map { it.toLocalDate() }.toSet()
    }
}
