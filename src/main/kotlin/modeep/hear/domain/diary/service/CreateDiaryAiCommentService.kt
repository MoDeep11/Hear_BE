package modeep.hear.domain.diary.service

import modeep.hear.domain.common.component.GetDataForRequestComponent
import modeep.hear.domain.diary.port.dto.result.CreateDiaryAiCommentResult
import modeep.hear.domain.diary.port.`in`.CreateDiaryAiCommentUseCase
import modeep.hear.domain.diary.port.out.external.FetchDiaryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateDiaryAiCommentService(
    private val fetchDiaryPort: FetchDiaryPort,
    private val getData: GetDataForRequestComponent
) : CreateDiaryAiCommentUseCase {
    override suspend fun execute(diaryId: UUID): CreateDiaryAiCommentResult {
        val (userInfo, diary) = getData.getUserInfoWithDiary(diaryId)

        val aiComment = fetchDiaryPort.addComment(userInfo, diary)
        return CreateDiaryAiCommentResult.Companion.from(aiComment)
    }
}