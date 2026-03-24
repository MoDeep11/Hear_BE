package modeep.hear.infrastructure.adapter.`in`.chat.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class UploadImageInChatRequest(
    @field:NotEmpty
    val imageUrls: List<@NotBlank String>
)
