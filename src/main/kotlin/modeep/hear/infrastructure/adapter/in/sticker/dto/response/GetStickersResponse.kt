package modeep.hear.infrastructure.adapter.`in`.sticker.dto.response

import java.util.UUID

data class GetStickersResponse(
    val stickerId: UUID,
    val imageUrl: String,
    val keyword: String?
)
