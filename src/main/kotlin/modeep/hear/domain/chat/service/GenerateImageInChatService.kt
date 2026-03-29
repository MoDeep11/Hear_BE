package modeep.hear.domain.chat.service

import modeep.hear.domain.chat.exception.ChatErrorCode
import modeep.hear.domain.chat.model.AiImageTask
import modeep.hear.domain.chat.port.dto.result.GenerateImageResult
import modeep.hear.domain.chat.port.`in`.GenerateImageInChatUseCase
import modeep.hear.domain.chat.port.out.command.CommandAiImageTaskPort
import modeep.hear.domain.chat.vo.AiImageTaskStatus
import modeep.hear.domain.diary.port.out.external.FetchDiaryImagePort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.GenerateImageInChatRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.GenerateImageInChatResponse
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.GenerateImageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class GenerateImageInChatService(
    private val commandAiImageTaskPort: CommandAiImageTaskPort,
    private val checkUserWithChatService: CheckUserWithChatService,
    private val fetchDiaryImagePort: FetchDiaryImagePort
) : GenerateImageInChatUseCase {
    override fun execute(
        chatId: UUID,
        request: GenerateImageInChatRequest
    ): GenerateImageResult {
        checkUserWithChatService.execute(chatId)
        if (!request.isReserved) throw BusinessException(ChatErrorCode.INVALID_GENERATION_REQUEST)

        val task = AiImageTask.create(
            chatId = chatId,
            status = AiImageTaskStatus.RESERVED
        )

        commandAiImageTaskPort.save(task)

        GenerateImageRequest(
            diaryId = ,
            userId = ,
            emotion = TODO(),
            content = TODO()
        )

        fetchDiaryImagePort.generateImage()
        // todo: ai 서버로 요청

        return GenerateImageResult(
            taskId = task.id,
            status = task.status,
            message = task.status.name,
        )
    }
}
