package modeep.hear.infrastructure.adapter.`in`.storage.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import modeep.hear.domain.storage.vo.ServiceType

data class GenerateUploadUrlRequest(
    @field:JsonAlias("file_name")
    @field:NotBlank
    val fileName: String,
    @field:JsonAlias("content_type")
    @field:NotBlank
    val contentType: String,
    @field:Min(0)
    val size: Long,
    val type: ServiceType
)
