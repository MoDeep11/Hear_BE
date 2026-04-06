package modeep.hear.domain.diary.service

import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.port.out.AiImageTaskPort
import modeep.hear.domain.chat.port.out.query.QueryChatPort
import modeep.hear.domain.chat.service.ChatCommandService
import modeep.hear.domain.common.event.EventPublisher
import modeep.hear.domain.diary.event.GenerateDiaryImageEvent
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.`in`.CreateDiaryUseCase
import modeep.hear.domain.diary.port.out.command.CommandDiaryPort
import modeep.hear.domain.diary.port.out.query.QueryDiaryImagePort
import modeep.hear.domain.user.event.IncreasedUserStatEvent
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.query.QueryUserPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CreateDiaryRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.CreateDiaryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateDiaryService(
    private val queryDiaryImagePort: QueryDiaryImagePort,
    private val eventPublisher: EventPublisher,
    private val commandDiaryPort: CommandDiaryPort,
    private val taskPort: AiImageTaskPort,
    private val queryChatPort: QueryChatPort,
    private val queryUserPort: QueryUserPort,
    private val chatCommandService: ChatCommandService
) : CreateDiaryUseCase {
    override suspend fun execute(request: CreateDiaryRequest): CreateDiaryResponse {
        val chatId = request.chatId
        val chat = queryChatPort.findById(chatId) ?: throw BusinessException(ChatErrorCode.CHAT_NOT_FOUND)
        val user = queryUserPort.findById(chat.userId) ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
        val userId = user.id
        chat.validateOwner(userId)

        val diary = Diary.create(
            userId = userId,
            content = request.content,
            emotion = request.emotion,
            tags = request.tags,
            sourceType = request.sourceType,
            chatId = chatId
        )

        val images = queryDiaryImagePort.findAllByChatId(chatId)
        diary.updateImages(images)

        commandDiaryPort.save(diary)
        chatCommandService.finishChatWithSuspend(user.id, chat)
        eventPublisher.publish(
            IncreasedUserStatEvent(
                userId = userId,
                diaryId = diary.id,
                emotion = diary.emotion,
                diaryDate = diary.baseTime.createdAt.toLocalDate()
            )
        )

        val task = taskPort.findByChatId(chatId)
        if (task != null) {
            task.assignDiary(diary.id)
            task.process()
            taskPort.save(task)

            eventPublisher.publish(GenerateDiaryImageEvent(userId, chatId, diary))
        }

        return CreateDiaryResponse.from(diary)
    }
}
