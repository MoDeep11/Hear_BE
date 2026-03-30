package modeep.hear.domain.chat.port.out.external

import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.dto.result.SendMessageResult
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import java.util.UUID

interface FetchMessagePort {
    suspend fun sendMessage(
        chatId: UUID,
        histories: List<History>,
        userInfo: UserInfo,
        message: Message
    ): SendMessageResult
}
