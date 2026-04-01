package modeep.hear.domain.storage.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.storage.port.out.PendingUploadPort
import modeep.hear.domain.storage.port.out.StoragePort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

private val log = KotlinLogging.logger {}

@Component
class StorageCleanupScheduler(
    private val pendingUploadPort: PendingUploadPort,
    private val storagePort: StoragePort
) {

    @Scheduled(cron = "0 0 * * * *")
    fun cleanupExpiredPendingUploads() {
        val expired = pendingUploadPort.findAllExpiredBefore(LocalDateTime.now())
        if (expired.isEmpty()) return

        val keys = expired.map { it.s3Key }

        runCatching {
            storagePort.deleteAllByKeys(keys)
        }.onSuccess {
            pendingUploadPort.deleteAll(expired)
            log.info { "Cleaned up ${expired.size} expired pending uploads" }
        }.onFailure { e ->
            log.error(e) { "Failed to clean up expired pending uploads" }
        }
    }
}
