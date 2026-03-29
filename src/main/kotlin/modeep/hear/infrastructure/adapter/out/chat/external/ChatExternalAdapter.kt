package modeep.hear.infrastructure.adapter.out.chat.external

import kotlinx.coroutines.reactor.awaitSingle
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.port.out.external.FetchChatPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.query.QueryUserProfilePort
import modeep.hear.domain.user.port.out.query.QueryUserStatPort
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
class ChatExternalAdapter(
    private val webClient: WebClient,
    private val queryUserStatPort: QueryUserStatPort,
    private val queryUserProfilePort: QueryUserProfilePort,
    private val securityPort: SecurityPort
) : FetchChatPort {
    override suspend fun initChat(chatId: UUID): InitChatResponse {
        val userId = securityPort.getCurrentUser().id
        val nickname = queryUserProfilePort.findByUserId(userId)?.nickname ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)
        val userStat = queryUserStatPort.findByUserId(userId) ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)

        val req = InitChatRequest(
            chatId = chatId,
            userInfo = UserInfo.of(userId, nickname, userStat)
        )

        return webClient.post()
            .uri("/internal/v1/chats")
            .bodyValue(req)
            .retrieve()
            .onStatus({ it.is5xxServerError }) { res ->
                res.bodyToMono<String>().flatMap {
                    Mono.error(BusinessException(GlobalErrorCode.AI_SERVER_ERROR))
                }
            }
            .bodyToMono<InitChatResponse>()
            .retryWhen(
                Retry.backoff(3, Duration.ofSeconds(2))
                    .filter { it is RuntimeException }
            )
            .awaitSingle()
    }
}
