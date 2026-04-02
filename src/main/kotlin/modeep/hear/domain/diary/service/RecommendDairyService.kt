package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.port.`in`.RecommendDairyUseCase
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.RecommendDiaryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RecommendDairyService(
    private val securityPort: SecurityPort,
    private val queryDiaryPort: QueryDiaryPort
) : RecommendDairyUseCase {
    override fun execute(): RecommendDiaryResponse {
        val user = securityPort.getCurrentUser()
        val diary = queryDiaryPort.findRandomByUserId(user.id)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)

        return RecommendDiaryResponse(
            diaryId = diary.id,
            targetDate = diary.baseTime.createdAt.toLocalDate(),
            emotion = diary.emotion
        )
    }
}