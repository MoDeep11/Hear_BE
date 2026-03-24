package modeep.hear.infrastructure.adapter.`in`.s3

import jakarta.validation.Valid
import modeep.hear.domain.s3.port.`in`.GeneratePreSignedUrlUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.s3.S3ApiDocument
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.GeneratePreSignedUrlRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GeneratePresignedUrlResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/files")
class S3WebAdapter(
    private val generatePreSignedUrlUseCase: GeneratePreSignedUrlUseCase
) : S3ApiDocument {

    @PostMapping
    override fun generatePresignedUrl(
        @RequestBody @Valid
        request: GeneratePreSignedUrlRequest
    ): ResponseEntity<ApiResult<GeneratePresignedUrlResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = generatePreSignedUrlUseCase.execute(request)
            )
        )
    }
}

// TODO: aws lambda로 동시성 처리 시도, Cleanup Scheduler로 DB에 없는 S3 파일들을 찾아 삭제