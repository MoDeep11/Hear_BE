package modeep.hear.infrastructure.adapter.out.chat.external.dto.request

import modeep.hear.domain.user.model.User
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo

data class SendMessageRequest(
    val userInfo: UserInfo,
    val message: String,
    val userAudioUrl: String,
    val history: List<History>,
    val chatId: String
) {
    companion object {
        fun toRequest(
            userInfo: UserInfo,
            message: String,
            userAudioUrl: String,
            history: List<History>,
            chatId: String
        ) = SendMessageRequest(
            userInfo = userInfo,
            message = message,
            userAudioUrl = userAudioUrl,
            history = history,
            chatId = chatId,
        )
    }

    private fun toUserInfo(user: User) = UserInfo(
        userId = user.id,
        nickname = TODO(),
        streakDays = TODO(),
        totalDiaries = TODO(),
        maxStreak = TODO()
    )
}