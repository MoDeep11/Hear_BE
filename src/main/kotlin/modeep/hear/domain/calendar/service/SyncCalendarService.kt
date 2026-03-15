package modeep.hear.domain.calendar.service

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.calendar.model.Calendar
import modeep.hear.domain.calendar.port.`in`.SyncCalendarUseCase
import modeep.hear.domain.calendar.port.out.FetchExternalCalendarPort
import modeep.hear.domain.calendar.service.component.QueryCalendarComponent
import modeep.hear.domain.calendar.service.component.SaveCalendarComponent
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class SyncCalendarService(
    private val fetchExternalCalendarPort: FetchExternalCalendarPort,
    private val queryCalendarComponent: QueryCalendarComponent,
    private val saveCalendarComponent: SaveCalendarComponent
) : SyncCalendarUseCase {
    override fun execute(year: Int): List<Calendar> {
        val saved = queryCalendarComponent.exist(year)

        if (saved) {
            log.info { "$year 데이터가 이미 존재합니다. 조회를 생략합니다." }
            return queryCalendarComponent.find(year)
        }

        log.info { "$year 데이터 동기화 시작..." }
        val holidays = fetchExternalCalendarPort.fetch(year)

        return saveCalendarComponent.execute(year, holidays)
    }
}

// SaveCalendarService의 트랜잭션 내에서 외부 api 호출 방지
