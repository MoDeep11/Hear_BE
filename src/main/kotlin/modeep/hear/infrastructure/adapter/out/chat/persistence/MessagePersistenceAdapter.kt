package modeep.hear.infrastructure.adapter.out.chat.persistence

import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.out.command.CommandMessagePort
import modeep.hear.domain.chat.port.out.query.QueryMessagePort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.chat.persistence.mapper.MessageMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.repository.ChatRepository
import modeep.hear.infrastructure.adapter.out.chat.persistence.repository.MessageRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MessagePersistenceAdapter(
    private val messageRepo: MessageRepository,
    private val chatRepo: ChatRepository,
    private val mapper: MessageMapper
) : QueryMessagePort, CommandMessagePort {

    // --Query--//
    override fun findAllByChatId(chatId: UUID): List<Message> {
        return messageRepo.findAllByChatIdOrderByBaseTimeCreatedAtAsc(chatId)
            .map { mapper.toModel(chatId, it) }
    }

    // --Command--//
    override fun save(message: Message) {
        val chat = chatRepo.findByIdOrNull(message.chatId)
            ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        messageRepo.save(mapper.toEntity(chat, message))
    }
}
