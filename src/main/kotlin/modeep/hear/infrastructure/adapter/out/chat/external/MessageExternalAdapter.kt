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
import modeep.hear.domain.chat.port.out.external.FetchMessagePort
import modeep.hear.domain.chat.port.out.query.QueryMessagePort
import modeep.hear.domain.user.port.out.query.QueryUserPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.Duration
import java.util.UUID

@Component
class MessageExternalAdapter (
    private val webClient: WebClient,
    private val queryMessagePort: QueryMessagePort,
) : FetchMessagePort {
    override suspend fun sendMessage(chatId: UUID, message: Message): Message {
        val histories = queryMessagePort.findAllByChatId(chatId).map(History::from)

        // todo: 유저 프로필이랑 스탯 가져오는 포트 생성하기
        val request = SendMessageRequest(
            userInfo = UserInfo(
                userId = UUID.randomUUID(),
                nickname = "stub",
            ),
            message = message.message,
            userAudioUrl = message.voiceUrl,
            history = histories,
            chatId = chatId
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

