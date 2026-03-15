package modeep.hear.domain.user.model

import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.id.UserCalendarId
import modeep.hear.global.error.exception.BusinessException

data class UserCalendar(
    val id: UserCalendarId? = null,
    val hasDiary: Boolean = false,
    val emotion: Emotion? = null
) {
    init {
        when {
            hasDiary && emotion == null -> throw BusinessException(
                UserErrorCode.INVALID_VALUE,
                "일기가 있다면 감정은 null일 수 없습니다."
            )
            !hasDiary && emotion != null -> throw BusinessException(
                UserErrorCode.INVALID_VALUE,
                "일기가 없다면 감정은 null이어야 합니다."
            )
        }
    }
}
