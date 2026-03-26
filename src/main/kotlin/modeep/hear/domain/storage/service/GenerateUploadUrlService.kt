package modeep.hear.domain.storage.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.storage.exception.StorageErrorCode
import modeep.hear.domain.storage.vo.FileData
import modeep.hear.domain.storage.port.`in`.GenerateUploadUrlUseCase
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.GenerateUploadUrlRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GenerateUploadUrlResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GenerateUploadUrlService(
    private val storagePort: StoragePort,
    private val securityPort: SecurityPort
) : GenerateUploadUrlUseCase {
    companion object {
        private val allowedTypes = listOf("image/jpeg", "image/jpg", "image/png", "image/webp", "image/gif")
        private const val MAX_FILE_SIZE = 1024 * 1024 * 10L // 10MB
    }

    override fun execute(request: GenerateUploadUrlRequest): GenerateUploadUrlResponse {
        val user = securityPort.getCurrentUser()

        validateRequest(request)

        val uniqueFileName = "${UUID.randomUUID()}-${request.fileName}"
        val fullPath = "${request.type.folder}/${user.id}/$uniqueFileName"

        // S3Port를 통해 Pre-signed URL 발급
        val preSignedUrl = storagePort.generateUploadUrl(
            FileData(
                filePath = fullPath,
                contentType = request.contentType
            )
        )

        return preSignedUrl
    }

    fun validateRequest(request: GenerateUploadUrlRequest) {
        if (!allowedTypes.contains(request.contentType.lowercase())) {
            throw BusinessException(StorageErrorCode.NOT_ALLOW_EXTENSION)
        }
        if (request.size > MAX_FILE_SIZE) {
            throw BusinessException(StorageErrorCode.FILE_TOO_LARGE)
        }
    }
}
