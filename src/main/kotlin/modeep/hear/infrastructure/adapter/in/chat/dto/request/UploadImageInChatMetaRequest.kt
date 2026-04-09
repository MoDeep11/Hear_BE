package modeep.hear.infrastructure.adapter.`in`.chat.dto.request

import jakarta.validation.constraints.Min
import modeep.hear.domain.storage.vo.ImageAction

data class UploadImageInChatMetaRequest(
    val action: ImageAction,
    @field:Min(value = 0)
    val order: Int
)
