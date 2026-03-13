package modeep.hear.domain.calendar.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class CalendarErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode {
    CALENDAR_SYNC_PARTIAL_FAILED(HttpStatus.MULTI_STATUS, "CAL_002", "연간 달력 동기화 중 일부 월 데이터 갱신에 실패했습니다.")
}
