package modeep.hear.infrastructure.adapter.out.chat.external

import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.out.external.FetchChatPort
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.infrastructure.adapter.out.chat.external.dto.request.SendMessageRequest
import modeep.hear.infrastructure.adapter.out.chat.external.dto.response.SendMessageResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import kotlinx.coroutines.reactor.awaitSingle
import java.time.Duration

@Component
class ChatExternalAdapter (
    private val webClient: WebClient
) : FetchChatPort {
    override suspend fun sendMessage(request: SendMessageRequest): Message {
        val response = webClient.post()
            .uri("/internal/v1/chats/messages")
            .bodyValue(request)
            .retrieve()
            .onStatus({ it.is5xxServerError }) {
                Mono.error(RuntimeException("AI 서버 일시적 오류"))
            }
            .bodyToMono<SendMessageResponse>()
            // 지수 백오프(Exponential Backoff) 전략으로 3번 재시도
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                .filter { it is RuntimeException }
            )
            .awaitSingle()

        val messageType = if (response.aiAudioUrl != null) {
            MessageType.VOICE
        } else {
            MessageType.TEXT
        }

        return Message.create(
            chatId = response.chatId,
            sender = Sender.AI,
            message = response.aiResponseText,
            messageType = messageType,
            voiceUrl = response.aiAudioUrl
        )
    }
}

