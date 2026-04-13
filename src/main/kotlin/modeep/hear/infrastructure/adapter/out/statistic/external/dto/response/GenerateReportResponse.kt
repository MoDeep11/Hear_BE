package modeep.hear.infrastructure.adapter.out.statistic.external.dto.response

import java.time.YearMonth
import java.util.UUID

data class GenerateReportResponse(
    val userId: UUID,
    val yearMonth: YearMonth,
    val aiReportComment: String
)
