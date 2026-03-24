package modeep.hear.domain.s3.port.`in`

import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.GetPreSignedUrlRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GetPresignedUrlResponse

interface GetPreSignedUrlUseCase {
    fun execute(request: GetPreSignedUrlRequest): GetPresignedUrlResponse
}
