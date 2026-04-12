package modeep.hear.infrastructure.adapter.out.chat.external

import kotlinx.coroutines.reactor.awaitSingle
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.dto.result.SendMessageResult
import modeep.hear.domain.chat.port.out.external.FetchMessagePort
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.out.chat.external.dto.request.SendMessageRequest
import modeep.hear.infrastructure.adapter.out.chat.external.dto.response.SendMessageResponse
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.util.UUID

@Component
class MessageExternalAdapter(
    private val webClient: WebClient
) : FetchMessagePort {
    override suspend fun sendMessage(
        chatId: UUID,
        histories: List<History>,
        userInfo: UserInfo,
        message: Message
    ): SendMessageResult {
        val request = SendMessageRequest(
            userInfo = userInfo,
            message = message.message,
            userAudioUrl = message.voiceUrl,
            history = histories,
            sessionId = chatId
        )

        val response = webClient.post()
            .uri("/internal/v1/chats/messages")
            .bodyValue(request)
            .retrieve()
            .onStatus({ it.is5xxServerError }) { res ->
                res.bodyToMono<String>().flatMap {
                    Mono.error(BusinessException(GlobalErrorCode.AI_SERVER_ERROR))
                }
            }
            .bodyToMono<SendMessageResponse>()
            .retryWhen(
                Retry.backoff(3, Duration.ofSeconds(2))
                    .maxBackoff(Duration.ofSeconds(10))
                    .filter { it is RuntimeException }
            )
            .checkpoint("AI message 전송 실패: chatId-[$chatId]")
            .awaitSingle()

        val aiMessage = Message.create(
            chatId = response.chatId,
            sender = Sender.AI,
            message = response.aiResponseText,
            messageType = if (response.aiAudioUrl != null) MessageType.VOICE else MessageType.TEXT,
            voiceUrl = response.aiAudioUrl
        )

        return SendMessageResult(
            userTranscription = response.userTranscription,
            status = response.status,
            suggestion = response.suggestion,
            aiMessage = aiMessage
        )
    }
}
