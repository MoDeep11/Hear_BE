package modeep.hear.infrastructure.adapter.out.sticker.external

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modeep.hear.infrastructure.adapter.out.sticker.event.GenerateStickerRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodilessEntity

private val log = KotlinLogging.logger {}

@Component
class StickerExternalAdapter(
    private val webClient: WebClient
) {
    suspend fun generateSticker(req: GenerateStickerRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                webClient.post()
                    .uri("/internal/v1/stickers")
                    .bodyValue(req)
                    .retrieve()
                    .awaitBodilessEntity()
            }.onFailure { e ->
                log.error(e) { "스티커 생성 요청 실패: user-[${req.userId}], diary-[${req.diaryId}]" }
            }
        }
    }
}
