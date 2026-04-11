package modeep.hear.infrastructure.adapter.out.chat.external.dto.request

import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import java.util.UUID

data class SendMessageRequest(
    val userInfo: UserInfo,
    val message: String,
    val userAudioUrl: String? = null,
    val history: List<History>,
    val sessionId: UUID
)
