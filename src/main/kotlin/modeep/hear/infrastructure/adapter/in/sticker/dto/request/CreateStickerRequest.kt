package modeep.hear.infrastructure.adapter.`in`.sticker.dto.request

import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class CreateStickerRequest(
    val userId: UUID,
    @field:NotBlank
    val imageUrl: String
)
