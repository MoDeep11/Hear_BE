package modeep.hear.infrastructure.adapter.out.diary.external

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.awaitSingle
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryAiComment
import modeep.hear.domain.diary.port.out.external.FetchDiaryPort
import modeep.hear.domain.diary.vo.DiaryAiCommentStatus
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import modeep.hear.infrastructure.adapter.out.diary.external.dto.reponse.AddCommentResponse
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.AddCommentRequest
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.GenerateDiaryRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodilessEntity
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.util.UUID

private val log = KotlinLogging.logger {}

@Component
class DiaryExternalAdapter(
    private val webClient: WebClient
) : FetchDiaryPort {
    override suspend fun generateDiary(
        chatId: UUID,
        histories: List<History>,
        userInfo: UserInfo
    ) {
        val req = GenerateDiaryRequest(
            userInfo = userInfo,
            history = histories
        )

        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                webClient.post()
                    .uri("/internal/v1/diaries/generations")
                    .bodyValue(req)
                    .retrieve()
                    .awaitBodilessEntity()
            }.onFailure {
                log.error(it) { "일기 생성 요청 실패: chatId-[$chatId]" }
            }
        }
    }

    override suspend fun addComment(userInfo: UserInfo, diary: Diary): DiaryAiComment {
        val req = AddCommentRequest(
            diaryId = diary.id,
            userId = userInfo.userId,
            nickname = userInfo.nickname,
            emotion = diary.emotion,
            content = diary.content,
            imageUrls = diary.diaryImages
                .mapNotNull { it.imageUrl }
        )

        val res = webClient.post()
            .uri("/internal/v1/diaries/comments")
            .bodyValue(req)
            .retrieve()
            .onStatus({ it.is5xxServerError }) { res ->
                res.bodyToMono<String>().flatMap {
                    Mono.error(BusinessException(GlobalErrorCode.AI_SERVER_ERROR))
                }
            }
            .bodyToMono<AddCommentResponse>()
            .retryWhen(
                Retry.backoff(3, Duration.ofSeconds(2))
            )
            .awaitSingle()

        return DiaryAiComment.create(
            diaryId = diary.id,
            content = res.aiComment,
            status = DiaryAiCommentStatus.COMPLETED
        )
    }
}
