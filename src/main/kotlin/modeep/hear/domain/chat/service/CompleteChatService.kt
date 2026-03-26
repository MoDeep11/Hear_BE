package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.port.`in`.CompleteChatUseCase
import modeep.hear.domain.chat.port.out.ChatPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class CompleteChatService(
    private val securityPort: SecurityPort,
    private val chatPort: ChatPort
) : CompleteChatUseCase {
    override fun execute(chatId: UUID) {
        val user = securityPort.getCurrentUser()
        val chat = chatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        chat.validateOwner(user.id)

        chat.completeChat()
        chatPort.save(chat)
    }
}
