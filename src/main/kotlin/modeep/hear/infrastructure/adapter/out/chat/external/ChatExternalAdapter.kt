package modeep.hear.infrastructure.adapter.out.chat.external

import kotlinx.coroutines.reactor.awaitSingle
import modeep.hear.domain.chat.port.out.external.FetchChatPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.out.chat.external.dto.request.InitChatRequest
import modeep.hear.infrastructure.adapter.out.chat.external.dto.response.InitChatResponse
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.util.UUID

@Component
class ChatExternalAdapter (
    private val webClient: WebClient
) : FetchChatPort {
    override suspend fun initChat(chatId: UUID) : InitChatResponse {
        val req = InitChatRequest(
            chatId = chatId,
            userInfo = UserInfo(
                userId = UUID.randomUUID(),
                nickname = "stub",
            )
        )

        return webClient.post()
            .uri("/internal/v1/chats")
            .bodyValue(req)
            .retrieve()
            .onStatus({ it.is5xxServerError }) {res ->
                res.bodyToMono<String>().flatMap {
                    Mono.error(BusinessException(GlobalErrorCode.AI_SERVER_ERROR))
                }
            }
            .bodyToMono<InitChatResponse>()
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                .filter { it is RuntimeException }
            )
            .awaitSingle()
    }
}

