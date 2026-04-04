package modeep.hear.domain.user.model

import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.id.UserCalendarId
import modeep.hear.global.error.exception.BusinessException
import java.util.UUID

data class UserCalendar(
    val id: UserCalendarId,
    val hasDiary: Boolean = false,
    val diaryId: UUID? = null,
    val emotion: Emotion? = null
) {
    init {
        when {
            hasDiary && diaryId == null -> throw BusinessException(
                UserErrorCode.INVALID_VALUE,
                "일기가 있다면 diaryId는 null일 수 없습니다."
            )
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

    companion object {
        fun create(
            id: UserCalendarId,
            hasDiary: Boolean = false,
            diaryId: UUID? = null,
            emotion: Emotion? = null
        ): UserCalendar {
            return UserCalendar(
                id = id,
                hasDiary = hasDiary,
                diaryId = diaryId,
                emotion = emotion
            )
        }
    }
}
