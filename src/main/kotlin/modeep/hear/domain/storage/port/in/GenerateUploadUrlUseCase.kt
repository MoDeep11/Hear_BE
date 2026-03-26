package modeep.hear.domain.storage.port.`in`

import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.GenerateUploadUrlRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GenerateUploadUrlResponse

interface GenerateUploadUrlUseCase {
    fun execute(request: GenerateUploadUrlRequest): GenerateUploadUrlResponse
}
