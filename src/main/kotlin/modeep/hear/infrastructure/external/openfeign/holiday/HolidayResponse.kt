package modeep.hear.infrastructure.external.openfeign.holiday

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
    val items: Any?,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int,
)

data class HolidayItem(
    val locdate: Int,
    val dateName: String,
    val isHoliday: String,
    val dateKind: String,
    val seq: Int,
)
