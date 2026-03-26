package modeep.hear.domain.storage.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.storage.port.`in`.GenerateUploadUrlUseCase
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.domain.storage.vo.FileData
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.GenerateUploadUrlRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GenerateUploadUrlResponse
import org.springframework.stereotype.Service

@Service
class GenerateUploadUrlService(
    private val storageManager: StorageManager,
    private val storagePort: StoragePort,
    private val securityPort: SecurityPort
) : GenerateUploadUrlUseCase {

    override fun execute(request: GenerateUploadUrlRequest): GenerateUploadUrlResponse {
        val user = securityPort.getCurrentUser()

        val fullPath = storageManager.generatePath(user.id, request)

        return storagePort.generateUploadUrl(FileData(fullPath, request.contentType))
    }
}
