package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.out.query.QueryChatPort
import modeep.hear.domain.user.model.User
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class CheckUserWithChatService(
    private val securityPort: SecurityPort,
    private val queryChatPort: QueryChatPort
) {
    suspend fun executeWithSuspend(chatId: UUID) : Pair<User, Chat> {
        val user = securityPort.getCurrentUser()
        val chat = queryChatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        chat.validateOwner(user.id)

        return user to chat
    }

    fun execute(chatId: UUID) : Pair<User, Chat> {
        val user = securityPort.getCurrentUser()
        val chat = queryChatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        chat.validateOwner(user.id)

        return user to chat
    }
}
