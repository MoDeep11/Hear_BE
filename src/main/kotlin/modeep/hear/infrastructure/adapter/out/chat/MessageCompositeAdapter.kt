package modeep.hear.infrastructure.adapter.out.chat

import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.infrastructure.adapter.out.chat.external.MessageExternalAdapter
import modeep.hear.infrastructure.adapter.out.chat.persistence.MessagePersistenceAdapter
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.UUID

@Primary
@Component
class MessageCompositeAdapter (
    private val persistenceAdapter: MessagePersistenceAdapter,
    private val externalAdapter: MessageExternalAdapter
) : MessagePort {

    //--Persistence--//
    override fun findAllByChatId(chatId: UUID): List<Message> =
        persistenceAdapter.findAllByChatId(chatId)

    override fun save(message: Message) =
        persistenceAdapter.save(message)

    //--External--//
    override suspend fun sendMessage(
        chatId: UUID,
        message: Message
    ): Message =
        externalAdapter.sendMessage(chatId, message)
}