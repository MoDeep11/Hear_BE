package modeep.hear.infrastructure.adapter.out.statistic.external

import kotlinx.coroutines.reactor.awaitSingle
import modeep.hear.domain.user.port.out.external.FetchMonthlyStatisticPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.out.statistic.external.dto.request.GenerateReportRequest
import modeep.hear.infrastructure.adapter.out.statistic.external.dto.response.GenerateReportResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

@Component
class StatisticExternalAdapter(
    private val webClient: WebClient
) : FetchMonthlyStatisticPort {
    override suspend fun generateReport(req: GenerateReportRequest): GenerateReportResponse {
        val response = webClient.post()
            .uri("/internal/v1/statistics/reports")
            .bodyValue(req)
            .retrieve()
            .onStatus({ it.is5xxServerError }) { res ->
                res.bodyToMono<String>().flatMap {
                    Mono.error(BusinessException(GlobalErrorCode.AI_SERVER_ERROR))
                }
            }
            .bodyToMono<GenerateReportResponse>()
            .retryWhen(
                Retry.backoff(3, Duration.ofSeconds(2))
                    .maxBackoff(Duration.ofSeconds(10))
                    .filter { it is RuntimeException }
            )
            .checkpoint("월간 AI 리포트 생성 실패: userId-[$req.userId], yearMonth-[$req.yearMonth]")
            .awaitSingle()
        return response
    }
}
