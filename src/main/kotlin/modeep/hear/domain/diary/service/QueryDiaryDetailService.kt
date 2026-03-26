package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.port.`in`.QueryDiaryDetailUseCase
import modeep.hear.domain.diary.port.out.QueryDiaryPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiaryDetailResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class QueryDiaryDetailService(
    private val queryDiaryPort: QueryDiaryPort,
    private val securityPort: SecurityPort
) : QueryDiaryDetailUseCase {
    override fun execute(diaryId: UUID): QueryDiaryDetailResponse {
        val diary = queryDiaryPort.findById(diaryId)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
        diary.validateOwner(securityPort.getCurrentUser().id)

        return QueryDiaryDetailResponse(
            id = diary.id,
            userId = diary.userId,
            content = diary.content,
            emotion = diary.emotion,
            tags = diary.tags,
            sourceType = diary.sourceType,
            chatId = diary.chatId,
            createdAt = diary.baseTime.createdAt.toLocalDate(),
            updatedAt = diary.baseTime.updatedAt.toLocalDate()
        )
    }
}
