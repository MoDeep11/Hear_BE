package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.common.event.EventPublisher
import modeep.hear.domain.diary.event.DiaryCreatedEvent
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryAiComment
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.diary.port.out.query.QueryDiaryImagePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class DiaryCommandService(
    private val queryDiaryImagePort: QueryDiaryImagePort,
    private val diaryPort: DiaryPort,
    private val eventPublisher: EventPublisher,
    private val securityPort: SecurityPort,
) {
    suspend fun createDiary(diary: Diary, chatId: UUID, userId: UUID) {
        diary.validateOwner(userId)

        val images = queryDiaryImagePort.findAllByChatId(chatId)
        diary.updateImages(images)

        diaryPort.save(diary)
        eventPublisher.publish(DiaryCreatedEvent(userId))
    }

    suspend fun saveDiaryWithAiComment(diary: Diary, aiComment: DiaryAiComment) {
        val userId = securityPort.getCurrentUser().id
        diary.validateOwner(userId)

        diary.diaryAiComment = aiComment
        diaryPort.save(diary)
    }
}
