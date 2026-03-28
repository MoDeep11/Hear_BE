package modeep.hear.infrastructure.adapter.out.chat.external

import kotlinx.coroutines.reactor.awaitSingle
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.dto.result.SendMessageResult
import modeep.hear.domain.chat.port.out.external.FetchMessagePort
import modeep.hear.domain.chat.port.out.query.QueryMessagePort
import modeep.hear.domain.chat.vo.MessageType
import modeep.hear.domain.chat.vo.Sender
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.query.QueryUserProfilePort
import modeep.hear.domain.user.port.out.query.QueryUserStatPort
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
    private val webClient: WebClient,
    private val queryMessagePort: QueryMessagePort,
    private val queryUserStatPort: QueryUserStatPort,
    private val queryUserProfilePort: QueryUserProfilePort,
    private val securityPort: SecurityPort
) : FetchMessagePort {
    override suspend fun sendMessage(chatId: UUID, message: Message): SendMessageResult {
        val histories = queryMessagePort.findAllByChatId(chatId).map(History::from)

        val userId = securityPort.getCurrentUser().id
        val nickname = queryUserProfilePort.findByUserId(userId)?.nickname ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)
        val userStat = queryUserStatPort.findByUserId(userId) ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)

        val request = SendMessageRequest(
            userInfo = UserInfo.of(userId, nickname, userStat),
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
            .retryWhen(
                Retry.backoff(3, Duration.ofSeconds(2))
                    .filter { it is RuntimeException }
            )
            .awaitSingle()

        val messageType = if (response.aiAudioUrl != null) {
            MessageType.VOICE
        } else {
            MessageType.TEXT
        }

        val aiMessage = Message.create(
            chatId = response.chatId,
            sender = Sender.AI,
            message = response.aiResponseText,
            messageType = messageType,
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
