package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.port.`in`.GetUserCalendarUseCase
import modeep.hear.domain.user.port.out.UserCalendarPort
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.GetUserCalendarResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class GetUserCalendarService(
    private val securityPort: SecurityPort,
    private val userCalendarPort: UserCalendarPort
) : GetUserCalendarUseCase {
    override fun execute(yearMonth: YearMonth): List<GetUserCalendarResponse> {
        val user = securityPort.getCurrentUser()
        val calendars = userCalendarPort.findAllByUserIdAndYearMonth(user.id, yearMonth)

        return calendars
            .filter { it.hasDiary }
            .mapNotNull { calendar ->
                val diaryId = calendar.diaryId ?: return@mapNotNull null
                val emotion = calendar.emotion ?: return@mapNotNull null
                GetUserCalendarResponse(
                    date = calendar.id.calendarDate,
                    diaryId = diaryId,
                    emotion = emotion
                )
            }
    }
}
