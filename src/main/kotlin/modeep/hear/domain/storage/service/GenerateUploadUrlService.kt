package modeep.hear.domain.storage.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.storage.model.PendingUpload
import modeep.hear.domain.storage.port.`in`.GenerateUploadUrlUseCase
import modeep.hear.domain.storage.port.out.PendingUploadPort
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.domain.storage.vo.FileData
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.GenerateUploadUrlRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.GenerateUploadUrlResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenerateUploadUrlService(
    private val storageManager: StorageManager,
    private val storagePort: StoragePort,
    private val pendingUploadPort: PendingUploadPort,
    private val securityPort: SecurityPort
) : GenerateUploadUrlUseCase {

    @Transactional
    override fun execute(req: GenerateUploadUrlRequest): GenerateUploadUrlResponse {
        val user = securityPort.getCurrentUser()

        val file = FileData(
            fileName = req.fileName,
            contentType = req.contentType,
            size = req.size,
            type = req.type,
            userId = user.id
        )

        val fullPath = storageManager.generatePath(file)
        val uploadTarget = file.copy(fileName = fullPath)

        pendingUploadPort.save(
            PendingUpload.create(
                userId = user.id,
                s3Key = fullPath,
                serviceType = file.type
            )
        )

        return storagePort.generateUploadUrl(uploadTarget)
    }
}
