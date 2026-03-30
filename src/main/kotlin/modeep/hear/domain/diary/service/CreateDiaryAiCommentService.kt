package modeep.hear.domain.diary.service

import modeep.hear.domain.common.component.GetDataForRequestComponent
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.`in`.CreateDiaryAiCommentUseCase
import modeep.hear.domain.diary.port.out.DiaryPort
import org.springframework.stereotype.Service

@Service
class CreateDiaryAiCommentService(
    private val diaryPort: DiaryPort,
    private val getData: GetDataForRequestComponent,
    private val diaryCommandService: DiaryCommandService
) : CreateDiaryAiCommentUseCase {
    override suspend fun execute(diary: Diary) {
        val userInfo = getData.getUserInfoOnly()
        val aiComment = diaryPort.addComment(userInfo, diary)
        diaryCommandService.saveDiaryWithAiComment(diary, aiComment)
    }
}
