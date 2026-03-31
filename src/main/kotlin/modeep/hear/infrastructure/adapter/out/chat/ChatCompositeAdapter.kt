package modeep.hear.infrastructure.adapter.out.chat

import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.out.ChatPort
import modeep.hear.infrastructure.adapter.out.chat.external.ChatExternalAdapter
import modeep.hear.infrastructure.adapter.out.chat.external.dto.response.InitChatResponse
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import modeep.hear.infrastructure.adapter.out.chat.persistence.ChatPersistenceAdapter
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.UUID

@Primary
@Component
class ChatCompositeAdapter(
    private val persistenceAdapter: ChatPersistenceAdapter,
    private val externalAdapter: ChatExternalAdapter
) : ChatPort {
    // --Persistence--//
    override fun findById(chatId: UUID): Chat? =
        persistenceAdapter.findById(chatId)

    override fun existsById(chatId: UUID): Boolean =
        persistenceAdapter.existsById(chatId)

    override fun save(chat: Chat) =
        persistenceAdapter.save(chat)

    override fun delete(chatId: UUID) =
        persistenceAdapter.delete(chatId)

    // --External--//
    override suspend fun initChat(
        chatId: UUID,
        userInfo: UserInfo
    ): InitChatResponse =
        externalAdapter.initChat(chatId, userInfo)
}
