package modeep.hear.infrastructure.adapter.`in`.chat.dto.response

import java.time.LocalDateTime

data class UploadImageInChatResponse(
    val imageUrls: List<String>,
    val updatedAt: LocalDateTime
)
