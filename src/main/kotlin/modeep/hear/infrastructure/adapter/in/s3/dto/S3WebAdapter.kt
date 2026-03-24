package modeep.hear.infrastructure.adapter.`in`.s3.dto

import jakarta.validation.Valid
import modeep.hear.domain.s3.port.`in`.GetPreSignedUrlUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.s3.S3ApiDocument
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.GetPreSignedUrlRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GetPresignedUrlResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/files")
class S3WebAdapter(
    private val getPreSignedUrlUseCase: GetPreSignedUrlUseCase
) : S3ApiDocument {
    override fun getPresignedUrl(
        @RequestBody @Valid request: GetPreSignedUrlRequest
    ): ResponseEntity<ApiResult<GetPresignedUrlResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = getPreSignedUrlUseCase.execute(request)
            )
        )
    }
}