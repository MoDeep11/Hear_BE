package modeep.hear.domain.storage.vo

import modeep.hear.domain.storage.exception.StorageErrorCode
import modeep.hear.global.error.exception.BusinessException
import java.util.UUID

data class FileData(
    val fileName: String?,
    val contentType: String?,
    val size: Long,
    val type: ServiceType,
    val userId: UUID
) {
    init {
        if (fileName.isNullOrBlank() || contentType.isNullOrBlank()) {
            throw BusinessException(StorageErrorCode.INVALID_FILE)
        }
    }
}
