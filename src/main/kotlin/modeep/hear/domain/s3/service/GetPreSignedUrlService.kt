package modeep.hear.domain.s3.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.s3.exception.AwsErrorCode
import modeep.hear.domain.s3.model.FileData
import modeep.hear.domain.s3.port.`in`.GetPreSignedUrlUseCase
import modeep.hear.domain.s3.port.out.S3Port
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.GetPreSignedUrlRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GetPresignedUrlResponse
import modeep.hear.infrastructure.adapter.out.s3.S3Adapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

class GetPreSignedUrlService(
    private val s3Port: S3Port,
    private val securityPort: SecurityPort
) : GetPreSignedUrlUseCase {
    companion object {
        private val allowedTypes = listOf("image/jpeg", "image/jpg", "image/png", "image/webp", "image/gif")
        private const val MAX_FILE_SIZE = 1024 * 1024 * 10L  // 10MB
    }

    override fun execute(request: GetPreSignedUrlRequest): GetPresignedUrlResponse {
        val user = securityPort.getCurrentUser()

        validateRequest(request)

        val uniqueFileName = "${UUID.randomUUID()}-${request.fileName}"
        val fullPath = "${request.type.folder}/${user.id}/$uniqueFileName"

        // S3Port를 통해 Pre-signed URL 발급
        val preSignedUrl = s3Port.getPreSignedUrl(
            FileData(
                filePath = fullPath,
                contentType = request.contentType
            )
        )

        return preSignedUrl
    }

    fun validateRequest(request: GetPreSignedUrlRequest) {
        if (allowedTypes.contains(request.contentType.lowercase())) {
            throw BusinessException(AwsErrorCode.NOT_ALLOW_EXTENSION)
        }
        if (request.size > MAX_FILE_SIZE) {
            throw BusinessException(AwsErrorCode.FILE_TOO_LARGE)
        }
    }
}