package modeep.hear.global.document.s3

import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.GeneratePreSignedUrlRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GeneratePresignedUrlResponse
import org.springframework.http.ResponseEntity

@Tag(name = "File Upload", description = "File upload를 위한 Pre-signed Url 발급 API")
interface S3ApiDocument {

    fun generatePresignedUrl(
        request: GeneratePreSignedUrlRequest
    ): ResponseEntity<ApiResult<GeneratePresignedUrlResponse>>
}
