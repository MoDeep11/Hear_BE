package modeep.hear.infrastructure.adapter.out.chat.persistence

import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.out.command.CommandChatPort
import modeep.hear.domain.chat.port.out.query.QueryChatPort
import modeep.hear.infrastructure.adapter.out.chat.persistence.mapper.ChatMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.repository.ChatRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ChatPersistenceAdapter(
    private val repo: ChatRepository,
    private val mapper: ChatMapper
) : QueryChatPort, CommandChatPort {
    // --Query--//
    override fun findById(chatId: UUID): Chat? =
        repo.findByIdOrNull(chatId)?.let { mapper.toModel(it) }

    override fun existsById(chatId: UUID): Boolean {
        return repo.existsById(chatId)
    }

    // --Command--//
    override fun save(chat: Chat) {
        val isExist = repo.existsById(chat.id)
        val entity = mapper.toEntity(chat, isNew = !isExist)
        repo.save(entity)
    }

    override fun delete(chatId: UUID) {
        repo.deleteById(chatId)
    }
}
