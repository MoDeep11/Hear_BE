package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.common.event.EventPublisher
import modeep.hear.domain.diary.event.DiaryDeletedEvent
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
    private val securityPort: SecurityPort,
    private val eventPublisher: EventPublisher
) : DeleteDiaryUseCase {
    override fun execute(diaryId: UUID) {
        val diary = diaryPort.findById(diaryId)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
        val userId = securityPort.getCurrentUser().id
        diary.validateOwner(userId)

        diaryPort.deleteById(diaryId)
        eventPublisher.publish(DiaryDeletedEvent(diaryId))
    }
}
