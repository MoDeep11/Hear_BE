package modeep.hear.infrastructure.adapter.`in`.chat.dto.response

import java.time.LocalDateTime

data class UploadImageResponse(
    val imageUrls: List<String>,
    val updatedAt: LocalDateTime
)
