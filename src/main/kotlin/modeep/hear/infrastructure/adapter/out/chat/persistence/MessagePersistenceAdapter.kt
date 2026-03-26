package modeep.hear.infrastructure.adapter.out.chat.persistence

import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.Message
import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.chat.mapper.MessageMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.repository.ChatRepository
import modeep.hear.infrastructure.adapter.out.chat.persistence.repository.MessageRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MessagePersistenceAdapter(
    private val messageRepo: MessageRepository,
    private val chatRepo: ChatRepository,
    private val mapper: MessageMapper
) : MessagePort {

    //--Command--//
    override fun save(message: Message) {
        val chat = chatRepo.findByIdOrNull(message.chatId)
            ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        messageRepo.save(mapper.toEntity(chat, message))
    }
}