package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.common.event.EventPublisher
import modeep.hear.domain.diary.port.`in`.CreateDiaryUseCase
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.diary.port.out.external.FetchDiaryPort
import modeep.hear.domain.diary.port.out.query.QueryDiaryImagePort
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.CreateDiaryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CreateDiaryService(
    private val diaryPort: FetchDiaryPort,
    private val securityPort: SecurityPort,
    private val diaryCommandService: DiaryCommandService,
) : CreateDiaryUseCase {
    override suspend fun execute(chatId: UUID): CreateDiaryResponse {
        val userId = securityPort.getCurrentUserId()

        val diary = diaryPort.generateDiary(chatId)

        diaryCommandService.saveDiary(diary, chatId, userId)

        return CreateDiaryResponse.from(diary)
    }
}
