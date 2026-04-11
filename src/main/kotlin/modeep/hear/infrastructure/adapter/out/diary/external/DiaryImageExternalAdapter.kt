package modeep.hear.infrastructure.adapter.out.diary.external

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modeep.hear.domain.diary.port.out.external.FetchDiaryImagePort
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.GenerateDiaryImageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodilessEntity

private val log = KotlinLogging.logger {}

@Component
class DiaryImageExternalAdapter(
    private val webClient: WebClient
) : FetchDiaryImagePort {
    override suspend fun generateImage(req: GenerateDiaryImageRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                webClient.post()
                    .uri("/internal/v1/diaries/images")
                    .bodyValue(req)
                    .retrieve()
                    .awaitBodilessEntity() // subscribe 대신 await 사용
            }.onFailure { e ->
                log.error(e) { "이미지 생성 요청 실패: diaryId-[${req.diaryId}]" }
            }
        }
    }
}
