package modeep.hear.infrastructure.adapter.`in`.storage.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import modeep.hear.domain.storage.vo.ServiceType

data class GenerateUploadUrlRequest(
    @field:NotBlank
    val fileName: String,
    @field:NotBlank
    val contentType: String,
    @field:Min(0)
    val size: Long,
    val type: ServiceType
)
