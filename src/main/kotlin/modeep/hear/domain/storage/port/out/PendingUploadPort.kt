package modeep.hear.domain.storage.port.out

import modeep.hear.domain.storage.model.PendingUpload
import java.time.LocalDateTime

interface PendingUploadPort {
    fun save(pendingUpload: PendingUpload)
    fun deleteByS3Key(s3Key: String)
    fun findAllExpiredBefore(now: LocalDateTime): List<PendingUpload>
    fun deleteAll(pendingUploads: List<PendingUpload>)
}
