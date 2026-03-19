package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.port.`in`.UpdateDiaryContentUseCase
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.UpdateDiaryContentRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UpdateDiaryContentService(
    private val diaryPort: DiaryPort,
    private val securityPort: SecurityPort
) : UpdateDiaryContentUseCase {
    override fun execute(
        diaryId: UUID,
        request: UpdateDiaryContentRequest
    ) {
        val user = securityPort.getCurrentUser()
        val diary = diaryPort.findById(diaryId)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)

        if (user.id != diary.userId) {
            throw BusinessException(DiaryErrorCode.CANNOT_DELETE_DIARY)
        }

        diary.updateContent(
            diary.content
        )

        diaryPort.save(diary)
    }
}
