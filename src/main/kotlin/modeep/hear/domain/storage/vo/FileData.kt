package modeep.hear.domain.storage.vo

import modeep.hear.domain.storage.exception.StorageErrorCode
import modeep.hear.global.error.exception.BusinessException
import org.springframework.web.multipart.MultipartFile
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

    companion object {
        fun create(file: MultipartFile, type: ServiceType, userId: UUID): FileData {
            return FileData(
                file.originalFilename,
                file.contentType,
                file.size,
                type,
                userId
            )
        }
    }
}
