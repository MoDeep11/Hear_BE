package modeep.hear.infrastructure.adapter.out.chat.external.dto.request

import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import java.util.UUID

data class InitChatRequest(
    val chatId: UUID,
    val userInfo: UserInfo
)
