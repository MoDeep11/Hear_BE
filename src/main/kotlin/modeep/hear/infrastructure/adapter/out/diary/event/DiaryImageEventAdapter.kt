package modeep.hear.infrastructure.adapter.out.diary.event

import modeep.hear.domain.diary.event.DiaryImageDeletedEvent
import modeep.hear.domain.diary.event.GenerateDiaryImageEvent
import modeep.hear.domain.diary.port.out.external.FetchDiaryImagePort
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.GenerateDiaryImageRequest
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DiaryImageEventAdapter(
    private val storagePort: StoragePort,
    private val fetchDiaryImagePort: FetchDiaryImagePort
) {
    @Async("storageAsyncExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleImageDeletion(event: DiaryImageDeletedEvent) {
        if (event.urls.isNotEmpty()) {
            storagePort.deleteAll(event.urls)
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    suspend fun generateDiaryImage(event: GenerateDiaryImageEvent) {
        fetchDiaryImagePort.generateImage(GenerateDiaryImageRequest.from(event))
    }
}
