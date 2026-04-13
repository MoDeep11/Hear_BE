package modeep.hear.domain.diary.service

import modeep.hear.domain.common.component.GetDataForRequestComponent
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.`in`.CreateDiaryAiCommentUseCase
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.query.QueryUserPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateDiaryAiCommentService(
    private val diaryPort: DiaryPort,
    private val getData: GetDataForRequestComponent,
    private val diaryCommandService: DiaryCommandService,
    private val queryUserPort: QueryUserPort
) : CreateDiaryAiCommentUseCase {
    override suspend fun execute(diary: Diary) {
        val user = queryUserPort.findById(diary.userId)
            ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
        val userInfo = getData.getUserInfoOnly(user)
        val aiComment = diaryPort.addComment(userInfo, diary)
        diaryCommandService.saveDiaryWithAiComment(diary, aiComment)
    }
}
