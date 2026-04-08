package modeep.hear.domain.storage.port.`in`

import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.GenerateUploadUrlRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.GenerateUploadUrlResponse

interface GenerateUploadUrlUseCase {
    fun execute(req: GenerateUploadUrlRequest): GenerateUploadUrlResponse
}
