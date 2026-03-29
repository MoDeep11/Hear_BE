package modeep.hear.infrastructure.adapter.out.diary.external

import kotlinx.coroutines.reactor.awaitSingle
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryAiComment
import modeep.hear.domain.diary.port.out.external.FetchDiaryPort
import modeep.hear.domain.diary.vo.DiaryAiCommentStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import modeep.hear.infrastructure.adapter.out.diary.external.dto.reponse.AddCommentResponse
import modeep.hear.infrastructure.adapter.out.diary.external.dto.reponse.GenerateDiaryResponse
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.AddCommentRequest
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.GenerateDiaryRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.util.UUID

@Component
class DiaryExternalAdapter(
    private val webClient: WebClient
) : FetchDiaryPort {
    override suspend fun generateDiary(
        chatId: UUID,
        histories: List<History>,
        userInfo: UserInfo
    ): Diary {
        val req = GenerateDiaryRequest(
            userInfo = userInfo,
            history = histories
        )

        val res = webClient.post()
            .uri("/internal/v1/diaries/generations")
            .bodyValue(req)
            .retrieve()
            .onStatus({ it.is5xxServerError }) { res ->
                res.bodyToMono<String>().flatMap {
                    Mono.error(BusinessException(GlobalErrorCode.AI_SERVER_ERROR))
                }
            }
            .bodyToMono<GenerateDiaryResponse>()
            .retryWhen(
                Retry.backoff(3, Duration.ofSeconds(2))
                    .filter { it is RuntimeException }
            )
            .awaitSingle()

        return Diary.create(
            userId = userInfo.userId,
            content = res.content,
            emotion = res.emotion,
            tags = res.tags,
            sourceType = DiarySourceType.AI_MADE,
            chatId = chatId
        )
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
