package modeep.hear.infrastructure.adapter.`in`.diary.dto.request

import modeep.hear.domain.chat.vo.ChatStatus

@Deprecated("Not used")
data class CreateDiaryRequest(
    val status: ChatStatus
)
