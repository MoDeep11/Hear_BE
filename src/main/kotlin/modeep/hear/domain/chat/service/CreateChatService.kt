package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.model.Chat
import modeep.hear.domain.chat.port.`in`.CreateChatUseCase
import modeep.hear.domain.chat.port.out.command.CommandChatPort
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateChatService(
    private val securityPort: SecurityPort,
    private val chatPort: CommandChatPort
) : CreateChatUseCase {
    override fun execute(): CreateChatResponse {
        val user = securityPort.getCurrentUser()
        val newChat = Chat.create(user.id)

        chatPort.save(newChat)

        val initMessage = "안녕하세요! 오늘의 일기를 시작해볼까요?" // todo: 나중에 변경

        return CreateChatResponse(
            chatId = newChat.id,
            initialMessage = initMessage,
            createdAt = newChat.baseTime.createdAt
        )
    }
}
