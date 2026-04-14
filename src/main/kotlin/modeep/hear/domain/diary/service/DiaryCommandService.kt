package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryAiComment
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DiaryCommandService(
    private val diaryPort: DiaryPort,
    private val securityPort: SecurityPort
) {
    @Transactional
    suspend fun saveDiaryWithAiComment(diary: Diary, aiComment: DiaryAiComment) {
        val userId = securityPort.getCurrentUser().id
        diary.validateOwner(userId)

        diary.diaryAiComment = aiComment
        diaryPort.save(diary)
    }

    suspend fun getDiaryWithUserId(diaryId: UUID): Pair<Diary, UUID> {
        val diary = diaryPort.findById(diaryId) ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
        val userId = securityPort.getCurrentUser().id
        diary.validateOwner(userId)

        return Pair(diary, userId)
    }
}
