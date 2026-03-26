package modeep.hear.domain.storage.service

import modeep.hear.domain.storage.exception.StorageErrorCode
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.GenerateUploadUrlRequest
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class StorageManager {
    companion object {
        private val allowedTypes = listOf("image/jpeg", "image/jpg", "image/png", "image/webp", "image/gif")
        private const val MAX_FILE_SIZE = 1024 * 1024 * 10L // 10MB
    }

    fun generatePath(userId: UUID, request: GenerateUploadUrlRequest): String {
        validate(request)

        val uniqueFileName = "${UUID.randomUUID()}-${request.fileName}"
        val path = "${request.type.folder}/${userId}/$uniqueFileName"

        return path
    }

    fun validate(request: GenerateUploadUrlRequest) {
        if (!allowedTypes.contains(request.contentType.lowercase())) {
            throw BusinessException(StorageErrorCode.NOT_ALLOW_EXTENSION)
        }
        if (request.size > MAX_FILE_SIZE) {
            throw BusinessException(StorageErrorCode.FILE_TOO_LARGE)
        }
    }
}
