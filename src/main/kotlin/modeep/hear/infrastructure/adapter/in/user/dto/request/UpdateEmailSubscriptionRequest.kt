package modeep.hear.infrastructure.adapter.`in`.user.dto.request

import com.fasterxml.jackson.annotation.JsonAlias

data class UpdateEmailSubscriptionRequest(
    @field:JsonAlias("is_subscribed")
    val isSubscribed: Boolean
)
