package modeep.hear.domain.calendar.service

import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.`in`.SyncCalendarUseCase
import modeep.hear.domain.calendar.port.out.FetchExternalCalendarPort
import org.springframework.stereotype.Service

@Service
class SyncCalendarService(
    private val fetchExternalCalendarPort: FetchExternalCalendarPort,
    private val saveCalendarService: SaveCalendarService
) : SyncCalendarUseCase {
    override fun execute(year: Int, month: Int): List<Calendar> {
        val holidays = fetchExternalCalendarPort.fetch(year, month)
        return saveCalendarService.execute(year, month, holidays)
    }
}
