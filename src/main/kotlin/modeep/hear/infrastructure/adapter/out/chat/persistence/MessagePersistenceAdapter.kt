package modeep.hear.infrastructure.adapter.out.chat.persistence

import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.out.command.CommandMessagePort
import modeep.hear.domain.chat.port.out.query.QueryMessagePort
import modeep.hear.infrastructure.adapter.out.chat.persistence.mapper.MessageMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.repository.MessageRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MessagePersistenceAdapter(
    private val messageRepo: MessageRepository,
    private val mapper: MessageMapper
) : QueryMessagePort, CommandMessagePort {

    // --Query--//
    override fun findAllByChatId(chatId: UUID): List<Message> {
        return messageRepo.findAllByChatIdOrderByCreatedAt(chatId)
            .map { mapper.toModel(chatId, it) }
    }

    // --Command--//
    override fun save(message: Message) {
        messageRepo.save(mapper.toEntity(message))
    }
}
