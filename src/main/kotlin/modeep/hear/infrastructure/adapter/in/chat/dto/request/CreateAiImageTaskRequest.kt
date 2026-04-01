package modeep.hear.infrastructure.adapter.`in`.chat.dto.request

import com.fasterxml.jackson.annotation.JsonAlias

data class CreateAiImageTaskRequest(
    @field:JsonAlias("is_reserved")
    val isReserved: Boolean = true
)
