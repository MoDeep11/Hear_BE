package modeep.hear.domain.chat.service

import modeep.hear.domain.chat.port.`in`.FinishChatUseCase
import modeep.hear.domain.common.component.GetDataForRequestComponent
import modeep.hear.domain.diary.port.out.external.FetchDiaryPort
import modeep.hear.domain.user.model.User
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FinishChatService(
    private val checkUserWithChatService: CheckUserWithChatService,
    private val fetchDiaryPort: FetchDiaryPort,
    private val getData: GetDataForRequestComponent
) : FinishChatUseCase {
    override suspend fun execute(
        chatId: UUID,
        user: User
    ) {
        checkUserWithChatService.executeWithSuspend(chatId, user)
        val (histories, userInfo) = getData.getUserInfoWithHistories(chatId)
        fetchDiaryPort.generateDiary(chatId, histories, userInfo)
    }
}
