package modeep.hear.domain.user.model

import modeep.hear.domain.common.model.Emotion
import modeep.hear.domain.user.model.id.UserCalendarId

data class UserCalendar(
    val id: UserCalendarId? = null,
    val hasDiary: Boolean = false,
    val emotion: Emotion? = null,
)