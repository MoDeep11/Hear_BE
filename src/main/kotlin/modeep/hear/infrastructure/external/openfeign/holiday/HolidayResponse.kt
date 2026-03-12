package modeep.hear.infrastructure.external.openfeign.holiday

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class HolidayResponse(
    val response: HolidayResponseData,
)

data class HolidayResponseData(
    val header: HolidayHeader,
    val body: HolidayBody?,
)

data class HolidayHeader(
    val resultCode: String,
    val resultMsg: String,
)

data class HolidayBody(
    val items: HolidayItems?,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class HolidayItems(
    val item: List<HolidayItem> = emptyList()
)

data class HolidayItem(
    val locdate: Int,
    val dateName: String,
    val isHoliday: String,
    val dateKind: String,
    val seq: Int,
) {
    fun toLocalDate(): LocalDate =
        LocalDate.parse(this.locdate.toString(), HOLIDAY_DATE_FORMATTER)

    companion object {
        private val HOLIDAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd")
    }
}
