package modeep.hear.infrastructure.adapter.`in`.sticker.dto.request

import jakarta.validation.constraints.NotBlank
import modeep.hear.domain.sticker.vo.StickerStatus
import java.util.UUID

data class CreateStickerRequest(
    val taskId: UUID,
    val userId: UUID,
    val diaryId: UUID?,
    val status: StickerStatus,
    @field:NotBlank
    val imageUrl: String,
    val keyword: String? = null
)
