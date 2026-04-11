package modeep.hear.infrastructure.adapter.out.chat.external.dto.response

import com.fasterxml.jackson.annotation.JsonAlias
import java.util.UUID

data class InitChatResponse(
    @field:JsonAlias("chat_id", "session_id", "sessionId")
    val chatId: UUID,

    @field:JsonAlias("initial_message")
    val initialMessage: String
)
