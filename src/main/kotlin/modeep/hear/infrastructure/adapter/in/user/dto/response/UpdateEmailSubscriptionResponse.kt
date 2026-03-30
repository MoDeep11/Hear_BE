package modeep.hear.infrastructure.adapter.`in`.user.dto.response

import java.time.LocalDateTime

data class UpdateEmailSubscriptionResponse(
    val isSubscribed: Boolean,
    val updatedAt: LocalDateTime
)