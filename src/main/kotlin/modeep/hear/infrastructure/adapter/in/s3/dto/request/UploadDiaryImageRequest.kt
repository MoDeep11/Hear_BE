package modeep.hear.infrastructure.adapter.`in`.s3.dto.request

import jakarta.validation.constraints.Min
import java.util.UUID

data class UploadDiaryImageRequest(
    val imageUrl: String?,
    val id: UUID? = null,

    @field:Min(value = 0)
    val order: Int,
    val isDeleted: Boolean = false
)