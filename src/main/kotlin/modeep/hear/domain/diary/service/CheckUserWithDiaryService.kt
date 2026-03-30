package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class CheckUserWithDiaryService(
    private val securityPort: SecurityPort,
    private val queryDiaryPort: QueryDiaryPort
) {
    suspend fun executeWithSuspend(diaryId: UUID) : Diary {
        val user = securityPort.getCurrentUser()
        val diary = queryDiaryPort.findById(diaryId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        diary.validateOwner(user.id)

        return diary
    }

    fun execute(diaryId: UUID) {
        val user = securityPort.getCurrentUser()
        val diary = queryDiaryPort.findById(diaryId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        diary.validateOwner(user.id)
    }
}
