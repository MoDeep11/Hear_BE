package modeep.hear.infrastructure.adapter.out.diary.event

import modeep.hear.domain.diary.event.DiaryImageDeletedEvent
import modeep.hear.domain.storage.port.out.StoragePort
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DiaryImageEventAdapter(
    private val storagePort: StoragePort
) {
    @Async("storageAsyncExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleImageDeletion(event: DiaryImageDeletedEvent) {
        if (event.urls.isNotEmpty()) {
            storagePort.deleteAll(event.urls)
        }
    }
}