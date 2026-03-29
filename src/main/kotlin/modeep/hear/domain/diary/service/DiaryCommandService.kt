package modeep.hear.domain.diary.service

import modeep.hear.domain.common.event.EventPublisher
import modeep.hear.domain.diary.event.DiaryCreatedEvent
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.diary.port.out.query.QueryDiaryImagePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DiaryCommandService(
    private val queryDiaryImagePort: QueryDiaryImagePort,
    private val diaryPort: DiaryPort,
    private val eventPublisher: EventPublisher
) {
    @Transactional
    suspend fun saveDiary(diary: Diary, chatId: UUID, userId: UUID) {
        diary.validateOwner(userId)

        val images = queryDiaryImagePort.findAllByChatId(chatId)
        diary.updateImages(images)

        diaryPort.save(diary)
        eventPublisher.publish(DiaryCreatedEvent(userId))
    }
}
