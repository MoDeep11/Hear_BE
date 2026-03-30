package modeep.hear.infrastructure.adapter.`in`.user.dto.response

import java.time.LocalDateTime

data class
UpdateNicknameResponse(
    val nickname: String,
    val updatedAt: LocalDateTime
)