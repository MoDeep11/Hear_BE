package modeep.hear.infrastructure.adapter.out.chat.persistence

import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.out.ChatPort
import modeep.hear.infrastructure.adapter.out.chat.mapper.ChatMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.repository.ChatRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ChatPersistenceAdapter(
    private val repo: ChatRepository,
    private val mapper: ChatMapper
) : ChatPort {
    //--Query--//
    override fun findById(chatId: UUID): Chat {
        TODO("Not yet implemented")
    }

    override fun existsById(chatId: UUID): Boolean {
        TODO("Not yet implemented")
    }

    //--Command--//
    override fun save(chat: Chat) {
        TODO("Not yet implemented")
    }
}