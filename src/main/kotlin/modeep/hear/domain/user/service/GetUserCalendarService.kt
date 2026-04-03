package modeep.hear.domain.user.service

import modeep.hear.domain.user.port.`in`.GetUserCalendarUseCase
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.GetUserCalendarResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class GetUserCalendarService(

) : GetUserCalendarUseCase {
    override fun execute(yearMonth: YearMonth): List<GetUserCalendarResponse> {

    }
}