package modeep.hear.domain.common.component

import modeep.hear.domain.chat.port.out.MessagePort
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.User
import modeep.hear.domain.user.port.out.query.QueryUserPort
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
    private val queryDiaryPort: QueryDiaryPort,
    private val queryUserPort: QueryUserPort
) {
    fun getUserInfoWithHistories(chatId: UUID, user: User): Pair<List<History>, UserInfo> {
        val histories = messagePort.findAllByChatId(chatId).map(History::from)
        val userInfo = getUserInfoOnly(user)
        return histories to userInfo
    }

    fun getUserInfoWithDiary(userId: UUID, diaryId: UUID): Pair<UserInfo, Diary> {
        val user = queryUserPort.findById(userId) ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
        val userInfo = getUserInfoOnly(user)
        val diary = queryDiaryPort.findById(diaryId) ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
        return userInfo to diary
    }

    fun getUserInfoOnly(user: User): UserInfo {
        val profile = queryUserProfilePort.findByUserId(user.id)
            ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)
        val stat = queryUserStatPort.findByUserId(user.id)
            ?: throw BusinessException(UserErrorCode.USER_STAT_NOT_FOUND)

        return UserInfo.of(user.id, profile.nickname, stat)
    }
}
