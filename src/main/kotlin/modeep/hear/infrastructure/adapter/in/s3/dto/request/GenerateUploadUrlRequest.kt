package modeep.hear.infrastructure.adapter.`in`.s3.dto.request

import jakarta.validation.constraints.NotBlank
import modeep.hear.domain.storage.vo.ServiceType

data class GenerateUploadUrlRequest(
    @field:NotBlank
    val fileName: String,
    @field:NotBlank
    val contentType: String,
    val size: Long,
    val type: ServiceType
)
