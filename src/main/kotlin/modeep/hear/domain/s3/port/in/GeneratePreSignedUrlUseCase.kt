package modeep.hear.domain.s3.port.`in`

import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.GeneratePreSignedUrlRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GeneratePresignedUrlResponse

interface GeneratePreSignedUrlUseCase {
    fun execute(request: GeneratePreSignedUrlRequest): GeneratePresignedUrlResponse
}
