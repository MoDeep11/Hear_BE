package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.port.`in`.DeleteDiaryUseCase
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class DeleteDiaryService(
    private val diaryPort: DiaryPort,
    private val securityPort: SecurityPort
) : DeleteDiaryUseCase {
    override fun execute(diaryId: UUID) {
        val user = securityPort.getCurrentUser()
        val diary = diaryPort.findById(diaryId)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)

        if (user.id != diary.userId) {
            throw BusinessException(DiaryErrorCode.CANNOT_DELETE_DIARY)
        }

        diaryPort.deleteById(diaryId)
    }
}
