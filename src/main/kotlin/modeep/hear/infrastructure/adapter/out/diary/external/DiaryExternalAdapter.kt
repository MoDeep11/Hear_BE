package modeep.hear.infrastructure.adapter.out.diary.external

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryAiComment
import modeep.hear.domain.diary.port.out.external.FetchDiaryPort
import modeep.hear.domain.diary.vo.DiaryAiCommentStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.query.QueryUserProfilePort
import modeep.hear.domain.user.port.out.query.QueryUserStatPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import modeep.hear.infrastructure.adapter.out.diary.external.dto.reponse.AddCommentResponse
import modeep.hear.infrastructure.adapter.out.diary.external.dto.reponse.GenerateDiaryResponse
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.AddCommentRequest
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.GenerateDiaryRequest
import modeep.hear.infrastructure.adapter.out.diary.persistence.mapper.DiaryMapper
import modeep.hear.infrastructure.adapter.out.diary.persistence.repository.DiaryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.util.UUID

@Component
class DiaryExternalAdapter(
    private val webClient: WebClient,
    private val securityPort: SecurityPort,
    private val queryUserStatPort: QueryUserStatPort,
    private val queryUserProfilePort: QueryUserProfilePort,
    private val messagePort: MessagePort,
    private val diaryRepo: DiaryRepository,
    private val diaryMapper: DiaryMapper
) : FetchDiaryPort {
    override suspend fun generateDiary(chatId: UUID): Diary {
        val (histories, userInfo) = withContext(Dispatchers.IO) {
            val histories = messagePort.findAllByChatId(chatId).map(History::from)

            val user = securityPort.getCurrentUser()
            val profile = queryUserProfilePort.findByUserId(user.id)
                ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)
            val stat = queryUserStatPort.findByUserId(user.id)
                ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)

            histories to UserInfo.of(user.id, profile.nickname, stat)
        }

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
            chatId = chatId,
        )
    }

    override suspend fun addComment(diaryId: UUID): DiaryAiComment {
        val req = withContext(Dispatchers.IO) {
            val diary = diaryMapper.toModel(diaryRepo.findByIdOrNull(diaryId)
                ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND))
            val userId = securityPort.getCurrentUser().id
            diary.validateOwner(userId)
            val nickname = queryUserProfilePort.findByUserId(userId)?.nickname
                ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)

            AddCommentRequest(
                diaryId = diaryId,
                userId = userId,
                nickname = nickname,
                emotion = diary.emotion,
                content = diary.content,
                imageUrls = diary.diaryImages
                    .mapNotNull { it.imageUrl }
            )
        }

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
            diaryId = diaryId,
            content = res.aiComment,
            status = DiaryAiCommentStatus.COMPLETED
        )
    }
}
