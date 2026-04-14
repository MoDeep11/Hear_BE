package modeep.hear.domain.user.port.out.external

import modeep.hear.infrastructure.adapter.out.statistic.external.dto.request.GenerateReportRequest
import modeep.hear.infrastructure.adapter.out.statistic.external.dto.response.GenerateReportResponse

interface FetchMonthlyStatisticPort {
    suspend fun generateReport(req: GenerateReportRequest): GenerateReportResponse
}
