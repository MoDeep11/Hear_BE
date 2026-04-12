package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.port.`in`.QueryDiaryDetailUseCase
import modeep.hear.domain.diary.port.out.query.QueryDiaryImagePort
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiaryDetailResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class QueryDiaryDetailService(
    private val queryDiaryPort: QueryDiaryPort,
    private val securityPort: SecurityPort,
    private val queryDiaryImagePort: QueryDiaryImagePort
) : QueryDiaryDetailUseCase {
    override fun execute(diaryId: UUID): QueryDiaryDetailResponse {
        val diary = queryDiaryPort.findById(diaryId)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
        diary.validateOwner(securityPort.getCurrentUser().id)

        val diaryImages = queryDiaryImagePort.findAllByDiaryId(diaryId)
            .mapNotNull { it.imageUrl }

        return QueryDiaryDetailResponse(
            id = diary.id,
            userId = diary.userId,
            content = diary.content,
            emotion = diary.emotion,
            imageUrls = diaryImages,
            aiComment = diary.diaryAiComment?.content,
            tags = diary.tags,
            sourceType = diary.sourceType,
            chatId = diary.chatId,
            createdAt = diary.baseTime.createdAt.toLocalDate(),
            updatedAt = diary.baseTime.updatedAt.toLocalDate()
        )
    }
}
