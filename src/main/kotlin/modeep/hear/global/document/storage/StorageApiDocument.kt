package modeep.hear.global.document.storage

import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.GenerateUploadUrlRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.GenerateUploadUrlResponse
import org.springframework.http.ResponseEntity

@Tag(name = "Storage", description = "스토리지 관련 API")
interface StorageApiDocument {

    fun generateUploadUrl(
        request: GenerateUploadUrlRequest
    ): ResponseEntity<ApiResult<GenerateUploadUrlResponse>>
}
