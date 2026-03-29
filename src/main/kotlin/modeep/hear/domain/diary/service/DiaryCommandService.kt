package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryAiComment
import modeep.hear.domain.diary.port.out.DiaryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DiaryCommandService(
    private val diaryPort: DiaryPort,
    private val securityPort: SecurityPort
) {
    suspend fun saveDiaryWithAiComment(diary: Diary, aiComment: DiaryAiComment) {
        val userId = securityPort.getCurrentUser().id
        diary.validateOwner(userId)

        diary.diaryAiComment = aiComment
        diaryPort.save(diary)
    }
}
