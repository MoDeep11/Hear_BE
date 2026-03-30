package modeep.hear.domain.common.component

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.query.QueryUserProfilePort
import modeep.hear.domain.user.port.out.query.QueryUserStatPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class GetDataForRequestComponent(
    private val messagePort: MessagePort,
    private val queryUserStatPort: QueryUserStatPort,
    private val queryUserProfilePort: QueryUserProfilePort,
    private val securityPort: SecurityPort,
    private val queryDiaryPort: QueryDiaryPort
) {
    fun getUserInfoWithHistories(chatId: UUID): Pair<List<History>, UserInfo> {
        val histories = messagePort.findAllByChatId(chatId).map(History::from)
        val userInfo = getUserInfoOnly()
        return histories to userInfo
    }

    fun getUserInfoWithDiary(diaryId: UUID): Pair<UserInfo, Diary> {
        val userInfo = getUserInfoOnly()
        val diary = queryDiaryPort.findById(diaryId) ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
        return userInfo to diary
    }

    fun getUserInfoOnly(): UserInfo {
        val user = securityPort.getCurrentUser()
        val profile = queryUserProfilePort.findByUserId(user.id)
            ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)
        val stat = queryUserStatPort.findByUserId(user.id)
            ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)

        return UserInfo.of(user.id, profile.nickname, stat)
    }
}
