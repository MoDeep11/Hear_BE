package modeep.hear.domain.diary.port.out.external

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryAiComment
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import java.util.UUID

interface FetchDiaryPort {
    suspend fun generateDiary(
        chatId: UUID,
        histories: List<History>,
        userInfo: UserInfo
    )

    suspend fun addComment(userInfo: UserInfo, diary: Diary): DiaryAiComment
}
