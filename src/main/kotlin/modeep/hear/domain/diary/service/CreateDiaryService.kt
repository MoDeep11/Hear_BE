package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.common.component.GetDataForRequestComponent
import modeep.hear.domain.diary.port.`in`.CreateDiaryUseCase
import modeep.hear.domain.diary.port.out.external.FetchDiaryPort
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.CreateDiaryResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateDiaryService(
    private val diaryPort: FetchDiaryPort,
    private val securityPort: SecurityPort,
    private val diaryCommandService: DiaryCommandService,
    private val getData: GetDataForRequestComponent
) : CreateDiaryUseCase {
    override suspend fun execute(chatId: UUID): CreateDiaryResponse {
        val userId = securityPort.getCurrentUserId()

        val (histories, userInfo) = getData.getUserInfoWithHistories(chatId)
        val diary = diaryPort.generateDiary(chatId, histories, userInfo)

        diaryCommandService.createDiary(diary, chatId, userId)

        return CreateDiaryResponse.from(diary)
    }
}
